package com.sec.cryptohdslibrary.security;

import static org.junit.Assert.assertTrue;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;

public class CipherInstanceTest {

	private KeyPair keyPair;
	private String sentence;

	@Before
	public void setUp() {
		sentence = "testSentence";
		keyPair = KeyStoreImpl.generateKeyPair();
	}

	@After
	public void tearDown() {
		sentence = null;
		keyPair = null;
	}

	@Test
	public void testRSACipher() {
		// convert string to bytes
		byte[] sentenceBytes = sentence.getBytes();

		// cipher sentence
		byte[] cipheredSentence = CipherInstance.RSACipher(sentenceBytes, keyPair.getPublic());

		// decipher sentence
		byte[] decipheredSentence = CipherInstance.RSADecipher(cipheredSentence, keyPair.getPrivate());

		// check that the original and the deciphered sentence are the same
		assertTrue("Sentences should be equal!", new String(sentenceBytes).equals(new String(decipheredSentence)));
	}

	@Test
	public void testRSAEncode() {
		// get keys
		PublicKey publicKey = keyPair.getPublic();
		PrivateKey privateKey = keyPair.getPrivate();

		// encode keys
		String encodedPublicKey = CipherInstance.encodePublicKey(publicKey);
		String encodedPrivateKey = CipherInstance.encodePrivateKey(privateKey);

		// decode keys
		PublicKey decodedPublicKey = CipherInstance.decodePublicKey(encodedPublicKey);
		PrivateKey decodedPrivateKey = CipherInstance.decodePrivateKey(encodedPrivateKey);

		// check that the original and the decoded keys are the same
		assertTrue("Public keys should be equal!", publicKey.equals(decodedPublicKey));
		assertTrue("Private keys should be equal!", privateKey.equals(decodedPrivateKey));
	}

}
