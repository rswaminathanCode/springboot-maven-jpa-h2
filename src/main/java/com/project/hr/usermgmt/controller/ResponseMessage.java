package com.project.hr.usermgmt.controller;

public class ResponseMessage {

	private String message;
	private Object results;
	public ResponseMessage(String message, Object results) {
		super();
		this.message = message;
		this.results = results;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResults() {
		return results;
	}
	public void setResults(Object results) {
		this.results = results;
	}

}
