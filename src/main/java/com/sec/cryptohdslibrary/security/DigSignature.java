package com.sec.cryptohdslibrary.security;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class DigSignature {

	public static byte[] signMessage(byte[] message, PrivateKey privateKey) {
		try {
			Signature rsaForSign = Signature.getInstance("SHA256withRSA");
			rsaForSign.initSign(privateKey);
			rsaForSign.update(message);
			byte[] signature = rsaForSign.sign();

			return signature;

		} catch (NoSuchAlgorithmException | InvalidKeyException | SignatureException e ) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean verifyMessageSignature(PublicKey pubKeyClient, byte[] signature, byte[] message) {
		boolean isVerified = false;
		try {
			KeyFactory keyFact = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pubKeyClient.getEncoded());
			PublicKey pubKey = keyFact.generatePublic(publicKeySpec);

			Signature rsaForVerify = Signature.getInstance("SHA256withRSA");
			rsaForVerify.initVerify(pubKey);
			rsaForVerify.update(message);
			isVerified = rsaForVerify.verify(signature);
			return isVerified;

		} catch(NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException | SignatureException e) {
			e.printStackTrace();
		}
		return isVerified;
	}
}
