package com.example.demo.entity;

import java.util.List;

import com.example.demo.response.VaccineInfo;

public class Vaccine {

	private String status;
	private List<VaccineInfo> vaccineInfo;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<VaccineInfo> getVaccineInfo() {
		return vaccineInfo;
	}

	public void setVaccineInfo(List<VaccineInfo> vaccineInfo) {
		this.vaccineInfo = vaccineInfo;
	}
	

}