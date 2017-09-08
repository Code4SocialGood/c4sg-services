package org.c4sg.service;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * 
 * @author Ogwara O. Rowland
 * @since Jul 6, 2017
 *
 */
@Service
public interface EmailTemplateService {
	
	/**
	 * Auto generates a string representation for the email body
	 * @param mailContext
	 * @param template The template filename. Default location is resources/templates
	 * @return
	 */
	public String generateFromContext(Map<String, Object> mailContext,  String template);

}
