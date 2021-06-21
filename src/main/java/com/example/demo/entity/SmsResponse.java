package com.example.demo.entity;

import java.util.List;

public class SmsResponse {

	public boolean result;
    public String request_id;
    public List<String> message;
    
	public String getRequest_id() {
		return request_id;
	}
	public void setRequest_id(String request_id) {
		this.request_id = request_id;
	}
	public List<String> getMessage() {
		return message;
	}
	public void setMessage(List<String> message) {
		this.message = message;
	}
}


