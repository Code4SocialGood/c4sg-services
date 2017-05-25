package org.c4sg;

import org.c4sg.dto.slack.channel.Group;
import org.c4sg.service.slack.SlackClientService;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SlackClientServiceTest extends C4SGTest {

    @Autowired
    private SlackClientService slackClientService;


    @Before
    public void setup() throws Exception {
    }
    
	@After
	public void tearDown() throws Exception {
	}
//    Before running this test, make sure you have the rights to create private channels, and you have the right token set in properties
    @Ignore
    @Test
    public void testCreatePrivateChannel() throws Exception {
        Group testingPrivate1 = slackClientService.createPrivateChannel("testingPrivate2112");
        Assert.assertNotNull(testingPrivate1);

    }

}
