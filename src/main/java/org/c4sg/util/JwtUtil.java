package org.c4sg.util;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.spring.security.api.authentication.AuthenticationJsonWebToken;

/**
 * 
 * Utility class for JWT
 *
 */
public class JwtUtil {
	
	/**
	 * Checks if one of the claim scopes hold an ADMIN role
	 * @return True if admin
	 */
	public static boolean isAdmin() {
		AuthenticationJsonWebToken auth = (AuthenticationJsonWebToken) SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null && auth.getAuthorities() != null && auth.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
	        return true;
	    }
	    return false;
	}
	
	/**
	 * Checks if the email matches
	 * @param email Email address 
	 * @return True if match
	 */
	public static boolean match(String email) {
		AuthenticationJsonWebToken auth = (AuthenticationJsonWebToken) SecurityContextHolder.getContext().getAuthentication();
	    if (auth != null) {
	    	DecodedJWT jwt = (DecodedJWT) auth.getDetails();
	    	String emailFromClaim = jwt.getClaim("http://email").asString();
	    	if (email.equals(emailFromClaim))
	    		return true;
	    }
	    return false;
	}
}
