package ch.opentrainingcenter.otc.training.boundary;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.startsWith;

public class RegisterServiceIT {
    private final static Random random = new Random();

    @Test
    public void shouldRegisterUser() {
        // register
        final String username = generateUsername();
        final String password = "secret";

        given().baseUri("http://localhost:8282").when()//
                .body(createJSON(username, password))//
                .contentType("application/json")//
                .post("/trainingservice/api/users/register")//
                .then().statusCode(Response.Status.OK.getStatusCode());
//                .header("Baerer", is(""));

        given().baseUri("http://localhost:8282").when()//
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")//
                .contentType("application/json")//
                .post("/trainingservice/api/users/authenticate").then()//
                .statusCode(Response.Status.OK.getStatusCode())//
                .header("Authorization", startsWith("Bearer"));
    }

    private static String createJSON(final String username, final String password) {
        return "{\"firstName\": \"test\", \"lastName\": \"test\", \"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
    }

    private static String generateUsername() {
        return RandomStringUtils.randomAlphabetic(10) + "@abc.de";
    }
}
