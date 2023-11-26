package com.tcscontrol.control_backend.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.relatory.RelatoryService;
import com.tcscontrol.control_backend.relatory.model.RelatorioRequestDTO;

import org.springframework.web.bind.annotation.RequestBody;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/relatory")
@AllArgsConstructor
public class RelatoryController {

    private RelatoryService relatoryService;

    @PostMapping
    @ResponseBody
    @SecurityRequirement(name = "Bearer Authentication")
    public void serveFile(HttpServletResponse response, @RequestBody RelatorioRequestDTO relatorioRequestDTO) throws IOException {

        Resource file = relatoryService.loadAsResource(relatorioRequestDTO);
        response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        try (InputStream inputStream = file.getInputStream();
                OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }
    }


    
    
}
