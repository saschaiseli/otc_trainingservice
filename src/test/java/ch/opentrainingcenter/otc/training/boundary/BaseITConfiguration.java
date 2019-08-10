package ch.opentrainingcenter.otc.training.boundary;

import org.junit.jupiter.api.BeforeAll;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class BaseITConfiguration {
    public final static String PATH = "http://localhost:8800";
    private final static String PING = PATH + "/otc_trainingservice/api/ping";

    @BeforeAll
    static void waitForPing() {
        final Client client = ClientBuilder.newClient();
        boolean serviceAvailable = false;
        int waitOneMinute = 0;
        while (waitOneMinute < 60 && !serviceAvailable) {
            try {
                final String response = client.target(PING)
                        .request(MediaType.TEXT_PLAIN)
                        .get(String.class);
                serviceAvailable = "pong".equalsIgnoreCase(response);

            } catch (final ProcessingException p) {
                System.out.println("Service not reachable " + PING + " --> waiting 10 seconds");
                waitOneMinute++;
                try {
                    Thread.sleep(10000);
                } catch (final InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
