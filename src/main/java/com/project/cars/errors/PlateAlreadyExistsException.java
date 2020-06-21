package com.project.cars.errors;

public class PlateAlreadyExistsException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PlateAlreadyExistsException() {
        super("License plate already exists");
    }

}
