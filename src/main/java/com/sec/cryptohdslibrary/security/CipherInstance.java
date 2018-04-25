package com.sec.cryptohdslibrary.security;

import com.sec.cryptohdslibrary.envelope.Envelope;
import com.sec.cryptohdslibrary.envelope.Message;
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
import javax.crypto.spec.SecretKeySpec;


public class CipherInstance {

	/* --- RSA --- */

	private static final String RSA_METHOD = "RSA/ECB/PKCS1Padding";
	private static final String RSA = "RSA";

	/* --- AES --- */

	private static final String AES_METHOD = "AES/ECB/PKCS5Padding";
	private static final String AES = "AES";


	/* --- RSA METHODS --- */


	public static byte[] RSACipher(byte[] message, PublicKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_METHOD);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return cipher.doFinal(message);

		} catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] RSADecipher(byte[] encryptedMessage, PrivateKey publicKey) {
		try {
			Cipher cipher = Cipher.getInstance(RSA_METHOD);
			cipher.init(Cipher.DECRYPT_MODE, publicKey);
			return cipher.doFinal(encryptedMessage);

		} catch(NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodePublicKey(PublicKey publicKey) {
		KeyFactory fact = null;
		try {
			fact = KeyFactory.getInstance(RSA);
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
			KeyFactory fact = KeyFactory.getInstance(RSA);
			return fact.generatePublic(spec);

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String encodePrivateKey(PrivateKey privateKey) {
		try {
			KeyFactory fact = KeyFactory.getInstance(RSA);
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
			KeyFactory fact = KeyFactory.getInstance(RSA);
			PrivateKey privateKey = fact.generatePrivate(keySpec);
			Arrays.fill(clear, (byte) 0);

			return privateKey;

		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}


	/* --- AES METHODS --- */


	public static SecretKey generateAESKey(){
        KeyGenerator keyGenerator = null;
        try {
            keyGenerator = KeyGenerator.getInstance(AES);
            keyGenerator.init(128);
			return keyGenerator.generateKey();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

	public static SecretKey getAESKeyFromByte(byte[] encodedKey){
		return new SecretKeySpec(encodedKey, 0, encodedKey.length, AES);
	}
	
	public static SealedObject AESCipherMessage(Message message , SecretKey key){
		try {
			Cipher cipher = Cipher.getInstance(AES_METHOD);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new SealedObject(message, cipher);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	 public static Message AESDecipherMessage(SealedObject sealedEnvelope, SecretKey key){
		 try {
			 Cipher cipher = Cipher.getInstance(AES_METHOD);
			 cipher.init(Cipher.DECRYPT_MODE, key);
			 return (Message) sealedEnvelope.getObject(cipher);

		 }catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
				 IOException | IllegalBlockSizeException | BadPaddingException | ClassNotFoundException e) {
            e.printStackTrace();
		 }
		 return null;
	  }
}
