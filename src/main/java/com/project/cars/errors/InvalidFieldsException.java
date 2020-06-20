package com.project.cars.errors;

public class InvalidFieldsException extends Exception {


	private static final long serialVersionUID = 6363533470330935704L;

	public InvalidFieldsException() {
        super("Invalid fields");
    }
}