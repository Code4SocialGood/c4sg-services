package org.c4sg.service.impl;

import java.util.Map;

import org.c4sg.service.EmailTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class ThymeleafTemplateService implements EmailTemplateService {
	
	@Autowired
	private TemplateEngine engine;

	@Override
	public String generateFromContext(Map<String, Object> mailContext, String template) {
		Context ctx = new Context();
		ctx.setVariables(mailContext);
		return engine.process(template, ctx);
	}

}
