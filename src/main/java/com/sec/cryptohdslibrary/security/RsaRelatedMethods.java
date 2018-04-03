package com.sec.cryptohdslibrary.security;

import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;
import java.security.spec.*;
import java.security.PrivateKey;
import java.util.Arrays;

import javax.crypto.*;


public class RsaRelatedMethods {

	public static String bytesToString(byte[] bytes2convert) {
		return Base64.encodeBase64String(bytes2convert);
	}

	public static byte[] stringToBytes(String string2convert) {
		return Base64.decodeBase64(string2convert);
	}

	public static byte[] RSACipher(byte[] message, PublicKey privateKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			return cipher.doFinal(message);

		} catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] RSADecipher(byte[] encryptedMessage, PrivateKey pubKey) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, pubKey);
			return cipher.doFinal(encryptedMessage);

		} catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodePublicKey(PublicKey publicKey) {
		KeyFactory fact = null;
		try {
			fact = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec spec = fact.getKeySpec(publicKey, X509EncodedKeySpec.class);
			return Base64.encodeBase64String(spec.getEncoded());

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static PublicKey decodePublicKey(String publicKeyAsString) {
		try {
			byte[] data = Base64.decodeBase64(publicKeyAsString);
			X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			return fact.generatePublic(spec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodePrivateKey(PrivateKey privateKey) {
		try {
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec spec = fact.getKeySpec(privateKey, PKCS8EncodedKeySpec.class);
			byte[] packed = spec.getEncoded();
			String key64 = Base64.encodeBase64String(packed);

			Arrays.fill(packed, (byte) 0);
			return key64;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static PrivateKey decodePrivateKey(String privateKeyAsString) {
		try {
			byte[] clear = Base64.decodeBase64(privateKeyAsString);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(clear);
			KeyFactory fact = KeyFactory.getInstance("RSA");
			PrivateKey privateKey = fact.generatePrivate(keySpec);
			Arrays.fill(clear, (byte) 0);

			return privateKey;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}
}
