package com.tcscontrol.control_backend;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.tcscontrol.control_backend.file.StorageService;
import com.tcscontrol.control_backend.file.model.entity.StorageProperties;
import com.tcscontrol.control_backend.relatory.RelatoryService;


@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
@EnableScheduling
public class ControlBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService, RelatoryService relatoryService) {
		return (args) -> {
			storageService.init();
			relatoryService.init();
		};
	}
}
