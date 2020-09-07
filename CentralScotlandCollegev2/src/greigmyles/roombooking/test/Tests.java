package greigmyles.roombooking.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import greigmyles.roombooking.rooms.Client;

class Tests {

	/**
	 * 
	 */
	@Test
	void testClientEmailFail_1() {
		assertFalse(Client.isValidEmail.test("greig.mail.com"));
	}

	@Test
	void testClientEmailFail_2() {
		assertFalse(Client.isValidEmail.test("greig@mail"));
	}

	@Test
	void testClientEmailSuccess() {
		assertTrue(Client.isValidEmail.test("greig@mail.com"));
	}

	@Test
	void testClientNameFail_1() {
		assertFalse(Client.isValidName.test("greig"));
	}

	@Test
	void testClientNameFail_2() {
		assertFalse(Client.isValidName.test("GREIG"));
	}

	@Test
	void testClientNameSuccess() {
		assertTrue(Client.isValidName.test("Greig"));
	}

	@Test
	void testClientNumberFail_1() {
		assertFalse(Client.isValidNumber.test("7718620617"));
	}

	@Test
	void testClientNumberFail_2() {
		assertFalse(Client.isValidNumber.test("0771862061"));
	}

	@Test
	void testClientNumberSuccess() {
		assertTrue(Client.isValidNumber.test("07718620617"));
	}

}
