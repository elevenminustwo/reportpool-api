package tr.edu.akdeniz.reportpool;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import static com.jayway.restassured.RestAssured.*;

import com.jayway.restassured.RestAssured;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ReportpoolApplication.class)
@TestPropertySource(value={"classpath:applicationtest.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ReportpoolApplicationTest {
    @Value("${server.port}")
    int port;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void getUsersTest(){
        get("/api/tdd/getUser").getBody().print();
    }

    @Test
    public void getPersonsTest(){
        post("/api/tdd/getPersons").getBody().print();
    }

    @Test
    public void addReportTest() throws JsonProcessingException {
        post ("tdd/savereport").getBody().print();
    }
    @Test
    public void getUnitReportsTest(){
        get("tdd/unitreports/1").getBody().print();
    }
    @Test
    public void getAllReportsTest(){
        get("tdd/allreports").getBody().print();
    }

    @Before
    public void setBaseUri () {

        RestAssured.port = port;

        RestAssured.baseURI = "http://localhost"; // replace as appropriate

    }
}
