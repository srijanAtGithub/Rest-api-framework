package MavenProjectsAnotherOne;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import org.apache.logging.log4j.*;

public class Main {

    private static Logger log = LogManager.getLogger(Main.class.getName());

    //we use properties to standardize the variables
    //like, for example the Google map api baseURL, so it can be directly accessed from HOST for testing
    //so, we store all these in the env.properties files

    Properties prop = new Properties();

    @BeforeTest
    public void getData() throws IOException {
        //the location of env.properties, for the 'prop' to access
        FileInputStream fis = new FileInputStream("C:\\Users\\srija\\IdeaProjects\\Rest_Assured_First\\src\\files\\env.properties");
        prop.load(fis);     //to connect fis and getProperty("HOST");

        //examples, how to access data from env.properties
        prop.getProperty("HOST");
        prop.getProperty("KEY");
    }

    @Test
    public void Test() {

        log.info("HOST INFORMATION = https://weather.visualcrossing.com");

        //format
        /*
        Base URL

        given()
            request headers
            parameters
            request cookies

        when()
            get(resource)
            post(resource)
            put(resource)

        then()
            assertions to make sure that we are getting correct response, etc

        extract()
            we can pull out the body response
            (to extract it in some variable for future use)
        */

        //base URL
        RestAssured.baseURI = "https://weather.visualcrossing.com";

        //we need to tell all parameters and resources
        Response temp = given().
            param("key", "B62NH33T4NTVC36UGDYZBPBUN").
        when().
            get("/VisualCrossingWebServices/rest/services/timeline/Washington,DC").
        then().
            assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
            body("stations.KADW.id", equalTo("KADW")).and().        //"days[0].hours[0].humidity", equalTo("47.01")
            header("Connection", "keep-alive").extract().response();

        String temp2 = temp.asString();
        log.info("THIS IS A TEST FROM MAIN");
        System.out.println("DEMO TEST DEMO TEST DEMO TEST DEMO TEST DEMO TEST DEMO TEST DEMO TEST DEMO TEST ");
        //log.info(temp2);

        /*
            this one's for POST REQUEST example

            [Unit 5 - 018, Unit 6 - 019]

            RestAssured.baseURI = prop.getProperty("HOST");
            given().
                queryParam("key", prop.getProperty("KEY")).
                body(payLoad.getPostData()).
            when().
                post(resources.placePostData()).
            then().
                assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK"));
        */

        /*
            this one's for ADD AND DELETE REQUEST example

------------//TASK 1 - GRAB THE RESPONSE--------------------------------------------------------------------------------------------------------------
            RestAssured.baseURI = prop.getProperty("HOST");
            Response res =      //import package for 'Response'
            given().
                queryParam("key", prop.getProperty("KEY")).
                body(payLoad.getPostData()).
            when().
                post(resources.placePostData()).
            then().
                assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK")).
            extract().response();

------------//TASK 2 - GRAB THE PLACE ID FROM RESPONSE------------------------------------------------------------------------------------------------
            String responseString = res.asString();     //to convert the 'raw' response into string format
            System.out.println(responseString);     //to output response in the console

            JsonPath js = new JsonPath(responseString);     //to convert the String into Json format (for traversal through the data)
            String placeId = js.get("place_id");        //to get data from the json formatted data

            System.out.println(placeId);

------------//TASK 3 - PLACE THIS PLACE ID IN THE DELETE REQUEST--------------------------------------------------------------------------------------
            given().
                queryParam("key", "abcd1234").
                body("{ "place_id" : "placeId" }").
            when().
                post(resources.placePostData()).
            then().
                assertThat().statusCode(200).and().contentType(ContentType.JSON).and().
                body("status", equalTo("OK"));
        */
    }
}