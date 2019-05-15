package ch.opentrainingcenter.otc.training.boundary.security;

import javax.ejb.Stateless;

import org.mindrot.jbcrypt.BCrypt;

@Stateless
public class BCryptService {

	public String hashPassword(final String plainTextPassword) {
		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	public boolean checkPassword(final String plainPassword, final String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
}
