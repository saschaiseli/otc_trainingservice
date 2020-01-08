package ch.opentrainingcenter.otc.training.boundary;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.StringStartsWith.startsWith;

@Disabled
public class RegisterServiceIT extends BaseITConfiguration {


    @Test
    public void shouldRegisterUser() {
        // register
        final String username = generateUsername();
        final String password = "secret";
        doCreateUser(username, password);
        doLogin(username, password);
    }

    private static void doCreateUser(final String username, final String password) {
        given().baseUri(PATH).when()//
                .body(createJSON(username, password))//
                .contentType("application/json")//
                .post("/otc_trainingservice/api/users/register")//
                .then().statusCode(Response.Status.OK.getStatusCode());
    }

    private static String doLogin(final String username, final String password) {
        final io.restassured.response.Response response = given().baseUri(PATH).when()//
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")//
                .contentType("application/json")//
                .post("/otc_trainingservice/api/users/authenticate").then()//
                .statusCode(Response.Status.OK.getStatusCode())//
                .header("Authorization", startsWith("Bearer")).extract().response();
        return response.getHeader("Authorization");
    }

    private static String createJSON(final String username, final String password) {
        return "{\"firstName\": \"test\", \"lastName\": \"test\", \"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
    }

    private static String generateUsername() {
        return RandomStringUtils.randomAlphabetic(10) + "@abc.de";
    }
}
