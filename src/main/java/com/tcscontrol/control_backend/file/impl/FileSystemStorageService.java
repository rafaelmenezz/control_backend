package com.tcscontrol.control_backend.file.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.tcscontrol.control_backend.exception.IllegalRequestException;
import com.tcscontrol.control_backend.exception.RecordNotFoundException;
import com.tcscontrol.control_backend.exception.StorageException;
import com.tcscontrol.control_backend.exception.StorageFileNotFoundException;
import com.tcscontrol.control_backend.file.StorageService;
import com.tcscontrol.control_backend.file.model.dto.FileDTO;
import com.tcscontrol.control_backend.file.model.entity.StorageProperties;
import com.tcscontrol.control_backend.pessoa.user.model.UserRepository;
import com.tcscontrol.control_backend.pessoa.user.model.entity.User;
import com.tcscontrol.control_backend.utilitarios.UtilCast;
import com.tcscontrol.control_backend.utilitarios.UtilData;
import com.tcscontrol.control_backend.utilitarios.UtilObjeto;

@Service
public class FileSystemStorageService implements StorageService {

    private final Path rootLocation;
    private final UserRepository userRepository;

    public FileSystemStorageService(StorageProperties properties, UserRepository userRepository) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.userRepository = userRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public FileDTO store(MultipartFile file, Long id) throws IOException {
        String nomeFoto = "";
        if (file.isEmpty()) {
            throw new StorageException("Failed to store empty file.");
        }
        User user = new User();
        if (userRepository.findById(id).isPresent()) {
            user = userRepository.findById(id).get();
        }
        if (user != null) {
            nomeFoto = adicionaNomeFoto(id, file.getOriginalFilename());
            Path destinationFile = this.rootLocation.resolve(
                    Paths.get(nomeFoto))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            InputStream inputStream = file.getInputStream();
            if (!UtilObjeto.isEmpty(inputStream)) {
                user.setFtFoto(nomeFoto);
                userRepository.save(user);
                Files.copy(inputStream, destinationFile,
                        StandardCopyOption.REPLACE_EXISTING);
            } else {
                throw new StorageException("Failed to store file.");
            }
        }

        return new FileDTO("/api/users/"+ UtilCast.toString(id) +"/foto");
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(Long id) {
        return rootLocation.resolve(userRepository.findById(id).map(record -> record.getFtFoto())
                .orElseThrow(() -> new RecordNotFoundException("Imagem não encontrada")));
    }

    private Path load(String fileName) {
        return rootLocation.resolve(fileName);
    }

    @Override
    public Resource loadAsResource(Long id) {
        try {
            User user;
            if (userRepository.findById(id).isPresent()) {
                user = userRepository.findById(id).get();
            } else {
                throw new RecordNotFoundException("Usuário não encontrado");
            }
            if (user.getFtFoto() == null) {
                throw new RecordNotFoundException("Imagem não encontrada");
            }
            String filename = user.getFtFoto();
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new RecordNotFoundException("Imagem não encontrada");
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    private String adicionaNomeFoto(Long id, String fileName) {
        if (UtilObjeto.isEmpty(fileName) || UtilObjeto.isEmpty(id)) {
            throw new IllegalRequestException("Foto invalida!");
        }
        if (fileName.contains("..")) {
            throw new IllegalRequestException("Nome do arquivo incorreto [" + fileName + "]");
        }

        String extensao = fileName.substring(fileName.indexOf("."));
        StringBuilder nome = new StringBuilder();
        nome.append(UtilCast.toString(id))
                .append(UtilData.toString(new Date(), UtilData.FORMATO_DATAHORA_SEM_CARACTERE)).append(extensao);

        return nome.toString();
    }

}
