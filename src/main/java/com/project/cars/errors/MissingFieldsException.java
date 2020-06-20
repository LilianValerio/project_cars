package com.project.cars.errors;

public class MissingFieldsException extends Exception {


	private static final long serialVersionUID = 4918642273789779792L;

	public MissingFieldsException() {
        super("Missing fields");
    }
}