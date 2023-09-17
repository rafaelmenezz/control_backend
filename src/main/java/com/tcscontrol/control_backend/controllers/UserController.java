package com.tcscontrol.control_backend.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.tcscontrol.control_backend.enuns.DocumentoType;
import com.tcscontrol.control_backend.file.StorageService;
import com.tcscontrol.control_backend.file.model.dto.FileDTO;
import com.tcscontrol.control_backend.pessoa.user.UserService;
import com.tcscontrol.control_backend.pessoa.user.model.dto.ReqUpdatePassword;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserCreateDTO;
import com.tcscontrol.control_backend.pessoa.user.model.dto.UserDTO;
import com.tcscontrol.control_backend.utilitarios.UtilControl;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@Validated
@RestController
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final StorageService storageService;

    @GetMapping
    public List<UserCreateDTO> list() {
        return userService.list();
    }

    @GetMapping("/{id}")
    public UserCreateDTO findById(@PathVariable @NotNull @Positive Long id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserCreateDTO create(@RequestBody UserCreateDTO userCreateDto) {
        String password = UtilControl.gerarSenha(8);
        UserDTO userDTO = new UserDTO(userCreateDto.id(),
                userCreateDto.nmUsuario(),
                DocumentoType.CPF.getValue(),
                userCreateDto.nrMatricula(),
                userCreateDto.nrCpf(),
                new BCryptPasswordEncoder().encode(password),
                userCreateDto.ftFoto(),
                userCreateDto.contacts(),
                userCreateDto.flStatus(),
                userCreateDto.typeUser());
        return userService.create(userDTO, password);
    }

    @PatchMapping("/{id}")
    public UserCreateDTO update(@PathVariable Long id, @RequestBody @Valid UserCreateDTO userCreateDto) {
        return userService.update(id, userCreateDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable @NotNull @Positive Long id) {
        userService.delete(id);
    }

    @PutMapping("/{id}/change-password")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updatePassword(@PathVariable Long id, @RequestBody ReqUpdatePassword data) {
        userService.updatePassword(id, data);
    }

    @GetMapping("/{id}/photo")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable @NotNull @Positive Long id) {

        Resource file = storageService.loadAsResource(id);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @GetMapping("/{id}/foto")
    @ResponseBody
    public void load(HttpServletResponse response, @PathVariable @NotNull @Positive Long id) throws IOException {
        Resource imageResource = storageService.loadAsResource(id);
        response.setContentType("image/jpeg");
        try (InputStream inputStream = imageResource.getInputStream();
                OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }

    }

    @PostMapping("/{id}/")
    public FileDTO handleFileUpload(@RequestParam(name = "photo", required = true) MultipartFile file,
            @PathVariable @NotNull @Positive Long id) throws IOException {
        return storageService.store(file, id);
    }

}
