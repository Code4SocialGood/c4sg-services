package org.c4sg.exception;

public class C4SGException extends RuntimeException {

	private static final long serialVersionUID = 1L;

    private String errorMessage;

    public C4SGException() {
        super();
    }

    public C4SGException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
