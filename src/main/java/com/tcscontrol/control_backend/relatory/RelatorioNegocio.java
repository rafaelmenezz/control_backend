package com.tcscontrol.control_backend.relatory;

import org.springframework.stereotype.Component;

@Component
public interface RelatorioNegocio extends RelatoryService {

    public static final String JASPER_PATH = "src/main/resources/jasper/";
    public static final String JASPER_PREFIXO = "lista_";
    public static final String JASPER_SUFIXO = ".jasper";
}
