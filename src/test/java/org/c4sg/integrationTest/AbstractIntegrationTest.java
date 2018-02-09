package org.c4sg.integrationTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.c4sg.C4SgApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Abstract class for configure integration tests
 *
 * @author Andrey Mochalov (mochalovandrey@gmail.com)
 */
@SpringBootTest(classes = {C4SgApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AbstractIntegrationTest extends AbstractTestNGSpringContextTests {

    private EmbeddedMysql embeddedMysql;

    @BeforeSuite
    public void beforeSuite() {
        embeddedMysql = EmbeddedMysql.anEmbeddedMysql(
                MysqldConfig.aMysqldConfig(Version.v5_7_latest)
                        .withPort(3306)
                        .withUser("admin", "mysql")
                        .build())
                .addSchema("c4sg")
                .start();
    }

    String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @AfterSuite
    public void afterSuite() {
        if (embeddedMysql != null) {
            embeddedMysql.stop();
        }
    }
}
