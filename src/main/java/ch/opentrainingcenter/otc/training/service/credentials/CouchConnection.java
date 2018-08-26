package ch.opentrainingcenter.otc.training.service.credentials;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.ws.rs.client.Client;

@Dependent
public class CouchConnection {

	@Inject
	private Credentials credentials;

	public enum RequestType {
		GET, POST, PUT, DELETE
	}

	private String usernameAndPassword;
	private String authorizationHeaderName = "Authorization";
	private String authorizationHeaderValue;

	private String ifMatchHeaderName = "If-Match";

	private String dbName = "attendees";
	private Client client;

	boolean connected = false;
	private String url;

	public boolean connect(String dbName) {
		if (!connected && credentials != null) {

		}
		return connected;
	}

	public boolean isAccessible() {
		return connected;
	}

}
