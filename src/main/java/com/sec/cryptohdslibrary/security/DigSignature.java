package com.sec.cryptohdslibrary.security;

import java.io.*;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import java.util.List;

 
//SIGNATURES SO SAO USADAS NUM SENTIDO, no outro o servidor usa HMAC para n ter que criar uma signature a cada msg de resposta.TODO

public class DigSignature {

	public byte[] sign(byte[] data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
		
		// generating a signature
		Signature rsaForSign = Signature.getInstance("SHA256withRSA");
		rsaForSign.initSign(privateKey);
		rsaForSign.update(data);
		byte[] signature = rsaForSign.sign();
		return signature;
	}
	
	
	public boolean verifySignature(byte[] pubKeyClient,byte[] signature,byte[] data) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, InvalidKeySpecException {
		
		KeyFactory keyFact = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(pubKeyClient);
		PublicKey pubKey = keyFact.generatePublic(publicKeySpec);
		
		
		Signature rsaForVerify = Signature.getInstance("SHA256withRSA");
		rsaForVerify.initVerify(pubKey );
		rsaForVerify.update(data);
		boolean verifies = rsaForVerify.verify(signature);
		return verifies;
	}

	
	public byte[] timestamped(byte[] data){
		
		int ts = (int)(System.currentTimeMillis()/1000); //TODO verificar que a data esta correcta
		byte[] timeStamp = new byte[]{
		        (byte) (ts >> 24),
		        (byte) (ts >> 16),
		        (byte) (ts >> 8),
		        (byte) ts};
		        
		byte[] timeStampedData = new byte[timeStamp.length + data.length];
		System.arraycopy(data, 0, data,0, data.length);
		System.arraycopy(timeStamp,0, timeStampedData,data.length , timeStamp.length);
		return timeStampedData;
	}
	
	public boolean verifyTimeStamp(byte[] msg1, byte[] msg2) {//TODO Possivelmente em vez de usar timeStamps usar um numero que idenfica a mensagem em que vamos, incrementado pelo servidor.Mesmo esquema, + curto/facil de manipular.
		byte[] timeStampOnly1 = Arrays.copyOfRange(msg1,msg1.length-24,msg1.length);
		byte[] timeStampOnly2 = Arrays.copyOfRange(msg2,msg1.length-24,msg2.length);
		
		return Arrays.equals(timeStampOnly1,timeStampOnly2);	
	}
	
	
	
	
	
}
