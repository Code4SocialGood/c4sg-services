package org.c4sg.service;

public interface Auth0Service {
	
	String getAuth0ApiToken() throws Exception;
	String getAuth0UserId(String email) throws Exception;
	void deleteAuth0User(String userId) throws Exception;

}
