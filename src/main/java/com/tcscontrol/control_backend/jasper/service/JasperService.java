package com.tcscontrol.control_backend.jasper.service;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import io.github.classgraph.Resource;

@Service
public class JasperService {

    private static final String JASPER_PATH = "classpath:jasper/";
    private static final String JASPER_PREFIXO = "lista_";
    private static final String JASPER_SUFIXO = ".jasper";


    private Map<String, Object> params = new HashMap<>();

    public void addParams(String key, Object value) {
        this.params.put(key, value);
    }

    public byte[] exportarPDF(String code) {
        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(JASPER_PATH.concat(JASPER_PREFIXO).concat(code).concat(JASPER_SUFIXO));
        } catch (Exception e) {
            // TODO: handle exception
        }
        return bytes;
    }

}
