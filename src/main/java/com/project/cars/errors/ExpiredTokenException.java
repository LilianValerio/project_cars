package com.project.cars.errors;

public class ExpiredTokenException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4205611171356528781L;

	public ExpiredTokenException() {
        super("Unauthorized - invalid session");
    }

}