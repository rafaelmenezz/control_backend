package com.tcscontrol.control_backend.file.model.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "opt/imagens/user";
	private String jasperDiretorio = "opt/relatorios/jasper";

	public String getLocation() {
		return location;
	}

	public String getJasperDiretorio() {
		return jasperDiretorio;
	}

	public void setLocation(String location) {
		this.location = location;
	}
}
