package com.tcscontrol.control_backend.jasper_reports;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.springframework.stereotype.Service;

@Service
public class JasperService {

    private static final String JASPER_PATH = "src/main/resources/jasper/";
    private static final String JASPER_PREFIXO = "lista_";
    private static final String JASPER_SUFIXO = ".jasper";

    private Map<String, Object> params = new HashMap<>();

    // Para relatórios com imagem, isso é necessário
    public JasperService() {
        this.params.put("IMAGEM_DIRETORIO", JASPER_PATH);
    }   

    public void addParams(String key, Object value) {
        this.params.put(key, value);
    }

    public byte[] exportPDF(String code) throws Exception {
        byte[] bytes = null;
        try {
            File file = ResourceUtils.getFile(JASPER_PATH + JASPER_PREFIXO + code + JASPER_SUFIXO);
            JasperPrint print = JasperFillManager.fillReport(file.getAbsolutePath(), params);
            bytes = JasperExportManager.exportReportToPdf(print);
        } catch (JRException e) {
            e.printStackTrace();
        }
        return bytes;
    }
}