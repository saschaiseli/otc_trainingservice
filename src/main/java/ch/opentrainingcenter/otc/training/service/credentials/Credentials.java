package ch.opentrainingcenter.otc.training.service.credentials;

public class Credentials {

	private String username;
	private String password;
	private String url;

	public Credentials() {
	}

	public Credentials(String username, String password, String url) {
		this.url = url;
		this.username = username;
		this.password = password;

		if (this.username == null || this.password == null || this.url == null) {
			throw new RuntimeException(
					"Database cannot be accessed at this time, something is null. Passed in variables were "
							+ "username=" + username + ", password="
							+ ((password == null) ? "null" : "(non-null password)") + ", url=" + url);
		}
	}

	public String getUrl() {
		return url;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

}