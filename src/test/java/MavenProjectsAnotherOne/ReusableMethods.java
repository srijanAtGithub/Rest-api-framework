package MavenProjectsAnotherOne;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ReusableMethods {

    public static JsonPath rawToJson(Response r){
        String respon = r.asString();
        JsonPath x = new JsonPath(respon);
        return x;
    }

    public static String getSessionKey(){
        //creating the session

        RestAssured.baseURI = "http://localhost:8080";

        Response res = given().
                header("Content-Type", "application/json").
                body("{ \"username\": \"srijanprasad22\", \"password\": \"@2tbPz7eM5rUA!y\" }").
                when().post("/rest/auth/1/session").
                then().statusCode(200).
                extract().response();

        JsonPath js = ReusableMethods.rawToJson(res);
        String sessionid = js.get("session.value");

        return sessionid;
    }
}
