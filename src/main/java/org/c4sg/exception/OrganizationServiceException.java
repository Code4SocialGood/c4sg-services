package org.c4sg.exception;

public class OrganizationServiceException extends C4SGException {

	private static final long serialVersionUID = 1L;

	public static final String MSG_INVALID_INPUT = "Input is invalid.";

    private String errorMessage;

    public OrganizationServiceException() {
        super();
    }

    public OrganizationServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
