package MavenProjectsAnotherOne;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;

public class Main2 {

    private static Logger log = LogManager.getLogger(Main.class.getName());

    Properties prop = new Properties();

    @BeforeTest
    public void getData() throws IOException {
        //the location of env.properties, for the 'prop' to access
        FileInputStream fis = new FileInputStream("C:\\Users\\srija\\IdeaProjects\\Rest_Assured_First\\src\\files\\env.properties");
        prop.load(fis);     //to connect fis and getProperty("HOST");
    }

    @Test
    public void JiraAPI(){

        //creating issue/defect

        log.info("HOST INFORMATION = http://localhost:8080");

        RestAssured.baseURI = "http://localhost:8080";

        Response res = given().header("Content-Type", "application/json").
                header("Cookie", "JSESSIONID=" + ReusableMethods.getSessionKey()).
        body("{" +
                "\"fields\": {" +
            "\"project\": {" +
                "\"key\": \"CODE\"" +
            "}," +
            "\"summary\": \"DebitCard Defect\"," +
                "\"description\": \"Creating my second bug\"," +
                    "\"issuetype\": { " +
                "\"name\": \"Bug\"" +
            "}" +
        "}}").
        when().post("rest/api/2/issue").
        then().statusCode(201).extract().response();

        JsonPath js = ReusableMethods.rawToJson(res);
        String id = js.get("id");
        log.info(id);
        log.info("THIS IS A MESSAGE FROM MAIN2");
    }
}
