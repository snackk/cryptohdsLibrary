package com.sec.cryptohdslibrary.security;



import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.Signature;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.security.InvalidKeyException;
import java.security.spec.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.PrivateKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.DatatypeConverter;


public class RsaRelatedMethods {

		public static byte[] stringToBytes(String str) {
			return DatatypeConverter.parseBase64Binary(str);
		}

		public static String bytesToString(byte[] b) {
			return DatatypeConverter.printBase64Binary(b);
		}

		public static byte[] RSAcipher(String message, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			byte[] c_message = cipher.doFinal(message.getBytes("UTF-8"));
			return c_message;
		}
		
		public static byte[] RSAdecipher(String c_message, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException, IOException {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			byte[] message = cipher.doFinal(stringToBytes(c_message));
			return message;
		}

		public static PublicKey getClientPublicKey(String key) throws InvalidKeySpecException, NoSuchAlgorithmException {
			byte[] pk = stringToBytes(key);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pk);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			return publicKey;
		}
	
}
