package ch.opentrainingcenter.otc.training.service.credentials;

import java.util.Optional;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.Produces;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class CredentialsProducer {

	@Resource(lookup = "cloudant/url")
	protected String resourceUrl;

	@Resource(lookup = "cloudant/username")
	protected String resourceUsername;

	@Resource(lookup = "cloudant/password")
	protected String resourcePassword;

	@Inject
	@ConfigProperty(name = "VCAP_SERVICES")
	Optional<Credentials> cred;

	@Produces
	public Credentials newCredentials() {
		Credentials credentials = cred.orElse(null);
		if (credentials == null) {
			if ((("${env.CLOUDANT_URL}").equals(resourceUrl)) && (("${env.CLOUDANT_USERNAME}").equals(resourceUsername))
					&& (("${env.CLOUDANT_PASSWORD}").equals(resourcePassword)))
				credentials = null;
			else
				credentials = new Credentials(resourceUsername, resourcePassword, resourceUrl);
		}
		return credentials;
	}
}
