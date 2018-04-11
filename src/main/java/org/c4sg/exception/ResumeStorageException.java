package org.c4sg.exception;


public class ResumeStorageException extends RuntimeException{
	
		public ResumeStorageException(String message) {
	        super(message);
	    }
	    
	    public ResumeStorageException(String message, Throwable cause) {
	        super(message, cause);
	    }
	
}
