package com.project.cars.errors;

public class InvalidLoginOrPasswordException extends Exception {


	private static final long serialVersionUID = 1499286901422142252L;

	public InvalidLoginOrPasswordException() {
        super("Invalid login or password");
    }
}