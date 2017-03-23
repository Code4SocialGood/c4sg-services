package org.c4sg.controller;

import org.c4sg.service.slack.SlackClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/api/chat")
@Api(description = "Operations about slack chat", tags = "slack")
public class SlackController {

	private static Logger LOGGER = LoggerFactory.getLogger(SlackController.class);

	@Autowired
	private SlackClientService slackClientService;

	@RequestMapping(value = "/isJoinedChat", method = RequestMethod.GET)
	@ApiOperation(value = "Find user by ID", notes = "Returns a user")
	public String getUser(
			@ApiParam(value = "emailID of user", required = true) @RequestParam(required = true) String email) {
		Boolean isJoinedChat = slackClientService.isJoinedChat(email);
		if (isJoinedChat) {
			return "Y";
		} else {
			return "N";
		}
	}

}
