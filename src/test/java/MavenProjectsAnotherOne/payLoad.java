package MavenProjectsAnotherOne;

public class payLoad {

    public static String getPostData(){

        String body = "{" + "\"accuracy\" : 50," + "\"name\" : \"demo\"," + "\"address\" : \"pune\"" + "}";
        return body;
    }
}