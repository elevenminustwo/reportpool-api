package tr.edu.akdeniz.reportpool;

import com.sun.glass.ui.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import com.jayway.restassured.RestAssured;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReportpoolApplication.class)
@TestPropertySource(value={"classpath:applicationtest.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReportpoolApplicationTest {
    @Value("${server.port}")
    int port;
    //asd

    @Test
    public void getUsersTest(){
        get("/api/tdd/getUser");
    }

    @Before
    public void setBaseUri () {

        RestAssured.port = port;

        RestAssured.baseURI = "http://localhost"; // replace as appropriate

    }
}
