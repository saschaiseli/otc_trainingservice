package ch.opentrainingcenter.otc.training.boundary.security;

import javax.enterprise.context.RequestScoped;
import javax.ws.rs.core.HttpHeaders;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.impl.TextCodec;

@RequestScoped
public class JWTService {

	private static final String SECRET = "Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=";

	public String getSigningKey() {
		return SECRET;
	}

	public byte[] getSigner() {
		return TextCodec.BASE64.decode(SECRET);
	}

	public Claims getClaims(final HttpHeaders httpHeaders) {
		final String jwt = httpHeaders.getRequestHeaders().getFirst(HttpHeaders.AUTHORIZATION)
				.substring("Bearer".length()).trim();
		final Jws<Claims> claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt);
		final Claims body = claims.getBody();
		return body;
	}
}
