package com.project.cars.errors;

public class InvalidTokenException extends Exception {


	private static final long serialVersionUID = 1L;

	public InvalidTokenException() {
        super("Unauthorized");
    }

}