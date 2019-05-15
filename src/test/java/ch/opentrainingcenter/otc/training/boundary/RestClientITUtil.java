package ch.opentrainingcenter.otc.training.boundary;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class RestClientITUtil {

    private final static Client client = ClientBuilder.newClient();
    private final static String PATH = "http://localhost:8282/trainingservice/api/";
    private final static WebTarget webTarget = client.target(PATH);

    public static void doCreateUser(final String username, final String password) {
        final WebTarget api = webTarget.path("users/register");
        final Invocation.Builder builder = api.request(MediaType.APPLICATION_JSON);
        builder.post(Entity.json(createUserJSON(username, password)));
    }

    public static String doLogin(final String username, final String password) {
        final WebTarget api = webTarget.path("users/authenticate");
        final Invocation.Builder builder = api.request(MediaType.APPLICATION_JSON);
        final Response response = builder.post(Entity.json(createUserPwJSON(username, password)));
        return response.getHeaderString("Authorization");
    }

    private static String createUserPwJSON(final String username, final String password) {
        return "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
    }

    private static String createUserJSON(final String username, final String password) {
        return "{\"firstName\": \"test\", \"lastName\": \"test\", \"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
    }


}
