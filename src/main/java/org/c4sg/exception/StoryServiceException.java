package org.c4sg.exception;

public class StoryServiceException extends C4SGException {

	private static final long serialVersionUID = 1L;

    private String errorMessage;

    public StoryServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
