package com.tcscontrol.control_backend.file;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.tcscontrol.control_backend.file.model.dto.FileDTO;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

	FileDTO store(MultipartFile file, Long id) throws IOException;

	Stream<Path> loadAll();

	Path load(Long id);

	Resource loadAsResource(Long id);

	void deleteAll();
    
}
