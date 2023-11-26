package com.tcscontrol.control_backend.relatory.model;

public record RelatorioRequestDTO(
    String type,
    String nmRelatory,
    String dtStart,
    String dtEnd,
    Boolean fixo,
    Double vlMin,
    Double vlMax,
    String nmPatrimony,
    String nrSerie
) {}
