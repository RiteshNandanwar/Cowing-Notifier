package com.example.demo.response;

public class VaccineInfo {

	private String centerName;
	private Integer available_capacity;
	private Integer dose_1;
	private Integer dose_2;

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public Integer getAvailable_capacity() {
		return available_capacity;
	}

	public void setAvailable_capacity(Integer available_capacity) {
		this.available_capacity = available_capacity;
	}

	public Integer getDose_1() {
		return dose_1;
	}

	public void setDose_1(Integer dose_1) {
		this.dose_1 = dose_1;
	}

	public Integer getDose_2() {
		return dose_2;
	}

	public void setDose_2(Integer dose_2) {
		this.dose_2 = dose_2;
	}
	
}
