package org.c4sg.exception;

public class UserOrganizationException extends C4SGException{
    private String errorMessage;

    public UserOrganizationException() {
        super();
    }

    public UserOrganizationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
