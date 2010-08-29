package com.barefoot.bpositive.exceptions;

@SuppressWarnings("serial")
public class DonorExistsException extends Exception {

	private String message;
	
	public DonorExistsException() {
		super();
	}

	public DonorExistsException(String message) {
		super(message);
		this.message = message;
	}
	
	public String getError() {
		return message;
	}
	
}
