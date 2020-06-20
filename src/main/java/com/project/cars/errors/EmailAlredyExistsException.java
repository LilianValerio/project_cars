package com.project.cars.errors;

public class EmailAlredyExistsException extends Exception {

    private static final long serialVersionUID = 9081387822297882732L;

    public EmailAlredyExistsException() {
        super("E-mail already exists");
    }

}