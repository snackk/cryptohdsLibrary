package com.sec.cryptohdslibrary.security;

import static org.junit.Assert.assertTrue;

import java.security.KeyPair;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;

public class DigSignatureTest {

	private String message;
	private KeyPair keyPair;

	@Before
	public void setUp() {
		message = "testMessage";
		keyPair = KeyStoreImpl.generateKeyPair();
	}

	@After
	public void tearDown() {
		message = null;
		keyPair = null;
	}

	@Test
	public void testSignature() {
		// get message's bytes
		byte[] messageAsBytes = message.getBytes();

		// sign message
		byte[] signature = DigSignature.signMessage(messageAsBytes, keyPair.getPrivate());

		// verify signature
		boolean isAuthentic = DigSignature.verifyMessageSignature(keyPair.getPublic(), signature, messageAsBytes);

		// check that the message is authentic
		assertTrue("The message should be authentic!", isAuthentic);
	}
}
