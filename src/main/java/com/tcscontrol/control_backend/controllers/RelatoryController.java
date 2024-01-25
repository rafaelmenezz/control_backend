package com.tcscontrol.control_backend.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.tcscontrol.control_backend.enuns.RelatoryFormatType;
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
    public void serveFile(HttpServletResponse response, @RequestBody RelatorioRequestDTO relatorioRequestDTO)
            throws IOException {

        Resource file = relatoryService.loadAsResource(relatorioRequestDTO);

        if (RelatoryFormatType.PDF.getValue().equals(relatorioRequestDTO.type()))
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);

        if (RelatoryFormatType.EXCEL.getValue().equals(relatorioRequestDTO.type()))
            response.setContentType("application/vnd.ms-excel");

        try (InputStream inputStream = file.getInputStream();
                OutputStream outputStream = response.getOutputStream()) {
            IOUtils.copy(inputStream, outputStream);
        }
    }

    @PostMapping("/download")
    @ResponseBody
    @SecurityRequirement(name = "Bearer Authentication")
    public ResponseEntity<Resource> serveFileDownload(HttpServletResponse response,
            @RequestBody RelatorioRequestDTO relatorioRequestDTO) throws IOException {

        Resource file = relatoryService.loadAsResource(relatorioRequestDTO);

        if (RelatoryFormatType.PDF.getValue().equals(relatorioRequestDTO.type())) {
            response.setContentType(MediaType.APPLICATION_PDF_VALUE);
        } else if (RelatoryFormatType.EXCEL.getValue().equals(relatorioRequestDTO.type())) {
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        } else {
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"");

        response.setHeader(HttpHeaders.CACHE_CONTROL, "max-age=36000");
        response.setHeader(HttpHeaders.PRAGMA, "public");

 
        try (InputStream inputStream = file.getInputStream();
                OutputStream outputStream = response.getOutputStream()) {
            FileCopyUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok().build();
    }

}
