package org.c4sg;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.c4sg.entity.Organization;
import org.c4sg.entity.Project;
import org.c4sg.entity.User;
import org.c4sg.service.AsyncEmailService;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmailServiceTest {
	
	private String from = "info@code4socialgood.org";
	private String message = "You received an application from Code for Social Good. \" \n" + 
			"    				+ \"Please login to the dashboard to review the application.";
	private GreenMail mailer;
	
	@Autowired
	private AsyncEmailService mailService;
	
	@Before
	public void setUp() {
		mailer = new GreenMail(new ServerSetup(8825, null, "smtp"));
		mailer.start();
	}
	
	@After
	public void tearDown() {
		mailer.stop();
	}
	
	@Test
	public void testSendVolunteerApplicationEmail() throws MessagingException {
		
		List<String> skills = Arrays.asList("Tester", "Coder", "Cop");
		
		User user = new User();
		user.setEmail("rogwara@nimworks.com");
		user.setFirstName("Rowland");
		user.setLastName("Ogwara");
		user.setTitle("Coolio Developer");
		user.setChatUsername("rogwara");
		user.setIntroduction("Something very interesting about this person");
		
		Project project = new Project();
		project.setName("Test Project");
		
		Map<String, Object> mailContext = new HashMap<String, Object>();
    	mailContext.put("user", user);
    	mailContext.put("skills", skills);
    	mailContext.put("project", project);
    	mailContext.put("message", message);
    	
    	mailService.sendWithContext(from, user.getEmail(), "", "Test Email", "volunteer-application", mailContext);
    	
    	// received message
    	MimeMessage[] receivedMessages = mailer.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        
        MimeMessage msg = receivedMessages[0];
        
        assertThat(from, is(msg.getFrom()[0].toString()));
        assertThat("Test Email", is(msg.getSubject()));
	}
	
	@Test
	public void testSendVolunteerApplicantEmail() throws MessagingException {
		
		Organization org = new Organization();
		org.setName("Test Organization");
		org.setContactEmail("test@c4sg.org");
		org.setContactName("John Sloan");
		org.setContactPhone("9898989899");
		
		User user = new User();
		user.setEmail("test@c4sg.org");
		user.setFirstName("John");
		user.setLastName("Sloan");
		user.setChatUsername("jsloan");
		
		Project project = new Project();
		project.setId(110);
		project.setName("Test Project");
		
		Map<String, Object> mailContext = new HashMap<String, Object>();
    	mailContext.put("org", org);
    	mailContext.put("user", user);
    	mailContext.put("projectLink", "http://codeforsocialgood.org");
    	mailContext.put("project", project);
    	
    	mailService.sendWithContext(from, user.getEmail(), "", "Test Email", "applicant-application", mailContext);
    	
    	// received message
    	MimeMessage[] receivedMessages = mailer.getReceivedMessages();
        assertEquals(1, receivedMessages.length);
        
        MimeMessage msg = receivedMessages[0];
        
        assertThat(from, is(msg.getFrom()[0].toString()));
        assertThat("Test Email", is(msg.getSubject()));
	}

}
