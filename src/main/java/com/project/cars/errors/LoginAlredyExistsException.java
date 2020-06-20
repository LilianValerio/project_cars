package com.project.cars.errors;

public class LoginAlredyExistsException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -8571095482431797115L;

	public LoginAlredyExistsException() {
        super("Login already exists");
    }

}