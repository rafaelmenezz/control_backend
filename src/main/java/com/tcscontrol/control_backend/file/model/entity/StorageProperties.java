package com.tcscontrol.control_backend.file.model.entity;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    private String location = "opt/imagens/user";
	private String relatorios = "opt/relatorio";

	public String getLocation() {
		return location;
	}
	public String getRelatorios() {
		return relatorios;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setRelatorios(String location) {
		this.location = location;
	}
}
