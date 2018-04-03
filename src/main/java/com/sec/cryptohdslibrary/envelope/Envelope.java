package com.sec.cryptohdslibrary.envelope;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;
import com.sec.cryptohdslibrary.security.DigSignature;
import com.sec.cryptohdslibrary.security.RsaRelatedMethods;
import com.sec.cryptohdslibrary.service.dto.LedgerDTO;
import com.sec.cryptohdslibrary.util.Util;

import java.io.Serializable;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Envelope implements Serializable {

	
	
    private byte[] signedMessage;

    private LedgerDTO message;

    public Envelope() {}

    public Envelope(String ledgerName, KeyStoreImpl clientKeyStore) {

        /*Create LedgerDTO with his public key encode as base64*/
        LedgerDTO ledgerDTO = new LedgerDTO(ledgerName, RsaRelatedMethods.encodePublicKey(clientKeyStore.getkeyPairHDS().getPublic()));

        /*Store on the Envelope the Original Message. To later verify if Signature is Authentic*/
        this.setMessage(ledgerDTO);

        /*Sign the Message itself, and store it on the Envelope*/
        this.setSignedMessage(DigSignature.signMessage(Util.objectToByte(ledgerDTO), clientKeyStore.getkeyPairHDS().getPrivate()));
    }

    
    
    public String getCipheredEnvelope(String cryptoHdsPKey) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        /*TODO - Aqui da cagada... Nao da pa cifrar isto com RSA....*/
        /*TODO - optar por cifrar isto usando HMAC....*/
        /*Cipher the Envelop(this) with the Public Key of the Server(cryptoHdsPKey)*/
    	/*return RsaRelatedMethods.bytesToString(RsaRelatedMethods.RSACipher(Util.objectToByte(this), RsaRelatedMethods.decodePublicKey(cryptoHdsPKey)));*/
    
    	
    	final int keySize = 2048;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keySize);      
        KeyPair kp = keyPairGenerator.genKeyPair();
        /*Util.objectToByte(this)*/
    	
    	SecretKey aesKey = RsaRelatedMethods.generateAesKey();
    	byte[] aesKeyBytes = aesKey.getEncoded();
    	byte[] EncryptedData = RsaRelatedMethods.encryptAes(new String("olaoleola"),aesKey);
    	
    	Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    	rsa.init(Cipher.WRAP_MODE, kp.getPublic());
    	byte[] EncryptedKey = rsa.wrap(aesKey);
    	
    	byte[] EncryptedMessage = new byte[EncryptedKey.length + EncryptedData.length];
    	System.arraycopy(EncryptedKey, 0, EncryptedMessage, 0, EncryptedKey.length);
    	System.arraycopy(EncryptedData, 0, EncryptedMessage, EncryptedKey.length, EncryptedData.length);
    	
    	byte[] EncryptedKeyReceived = Arrays.copyOfRange(EncryptedMessage, 0, 16);
    	byte[] EncryptedDataReceived = Arrays.copyOfRange(EncryptedMessage, 17, EncryptedMessage.length);
    	
    	/*org.apache.commons.lang.ArrayUtils.reverse(EncryptedKeyReceived);*/
    
    	
    	System.out.println("EncryptedKeyReceivedSize:"+ EncryptedKeyReceived.length);
    	System.out.println("EncryptedDataReceivedSize:"+ EncryptedDataReceived.length);
    	
    	
    	
    	Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
    	rsaCipher.init(Cipher.UNWRAP_MODE, kp.getPrivate());
    	Key decryptedKey = rsaCipher.unwrap(EncryptedKey, "AES", Cipher.SECRET_KEY);
    	
    	System.out.println("Decrypted Key Length: " + decryptedKey.getEncoded().length);
    	System.out.println("EncryptedDataReceived:" + EncryptedDataReceived.length);
    	
    	SecretKeySpec decrypskeySpec = new SecretKeySpec(decryptedKey.getEncoded(), "AES");
    
    	Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    	cipher.init(Cipher.DECRYPT_MODE, decryptedKey);
    	String message = new String (cipher.doFinal(EncryptedDataReceived));

    	System.out.println(message);	
    	
    
    	return null;
    	
    }
    

    public byte[] getSignedMessage() {
        return signedMessage;
    }

    public void setSignedMessage(byte[] signedMessage) {
        this.signedMessage = signedMessage;
    }

    public LedgerDTO getMessage() {
        return message;
    }

    public void setMessage(LedgerDTO message) {
        this.message = message;
    }
}
