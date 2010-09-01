package com.barefoot.bpositive.exceptions;

@SuppressWarnings("serial")
public class RecordExistsException extends Exception {

	private String message;
	
	public RecordExistsException() {
		super();
	}

	public RecordExistsException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getError() {
		return message;
	}
	
}
