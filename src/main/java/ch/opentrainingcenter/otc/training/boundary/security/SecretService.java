package ch.opentrainingcenter.otc.training.boundary.security;

import javax.enterprise.context.RequestScoped;

import io.jsonwebtoken.impl.TextCodec;

@RequestScoped
public class SecretService {

	private static final String SECRET = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

	public String getSigningKey() {
		return SECRET;
	}

	public byte[] getSigner() {
		return TextCodec.BASE64.decode(SECRET);
	}
}
