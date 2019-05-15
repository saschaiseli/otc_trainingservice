package ch.opentrainingcenter.otc.training.boundary.security;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BCryptServiceTest {

	@Test
	void testHashPasswordSuccess() {
		final BCryptService service = new BCryptService();
		final String plainTextPassword = "secret";
		final String hashPassword1 = service.hashPassword(plainTextPassword);
		final String hashPassword2 = service.hashPassword(plainTextPassword);
		final String hashPassword3 = service.hashPassword(plainTextPassword);
		final String hashPassword4 = service.hashPassword(plainTextPassword);
		assertTrue(service.checkPassword(plainTextPassword, hashPassword1));
		assertTrue(service.checkPassword(plainTextPassword, hashPassword2));
		assertTrue(service.checkPassword(plainTextPassword, hashPassword3));
		assertTrue(service.checkPassword(plainTextPassword, hashPassword4));
	}

	@Test
	void testHashPasswordFailed() {
		final BCryptService service = new BCryptService();
		final String plainTextPassword = "secret";
		final String hashPassword = service.hashPassword(plainTextPassword);

		assertFalse(service.checkPassword("secret2", hashPassword));
	}

	@Test
	void testHashPasswordFailedWrong() {
		final BCryptService service = new BCryptService();
		final String plainTextPassword = "secret";

		assertThrows(IllegalArgumentException.class, () -> {
			service.checkPassword(plainTextPassword, "hashPassword");
		});
	}
}
