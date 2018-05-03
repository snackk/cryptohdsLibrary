package com.sec.cryptohdslibrary.envelope;

import java.io.IOException;
import java.io.Serializable;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;
import com.sec.cryptohdslibrary.security.CipherInstance;
import com.sec.cryptohdslibrary.util.Util;

public class Envelope implements Serializable {

	private static final long serialVersionUID = -3232620599416562172L;

	private String cipheredAESKey;

    private byte[] sealedMessage;

    private String clientPublicKey;

    public Envelope() {}

    /*Cipher Envelope with Message*/
    public void cipherEnvelope(Message message, String cryptoHdsPKey) throws IOException {

        /*Generate AES key*/
        SecretKey aesKey = CipherInstance.generateAESKey();

        /*Cipher AES key with public key of Server and store it*/
        this.cipheredAESKey = Util.bytesToString(CipherInstance.RSACipher(aesKey.getEncoded(),
                CipherInstance.decodePublicKey(cryptoHdsPKey)));

        /*Generate a sealed message with AES key and store it*/
        this.sealedMessage = Util.objectToByte(CipherInstance.AESCipherMessage(message, aesKey));
    }

    public Message decipherEnvelope(KeyStoreImpl keyStore) throws ClassNotFoundException, IOException {

        /*Decipher AES key with Server private key*/
        byte[] encodedKey = CipherInstance.RSADecipher(Util.stringToBytes(getCipheredAESKey()), keyStore.getkeyPairHDS().getPrivate());

		// Get object
		SealedObject sealedObject = (SealedObject) Util.byteToObject(this.sealedMessage);

        /*Build SecretKey from byte[]*/
        return CipherInstance.AESDecipherMessage(sealedObject, CipherInstance.getAESKeyFromByte(encodedKey));
    }

    public String getCipheredAESKey() {
        return cipheredAESKey;
    }

    public void setCipheredAESKey(String cipheredAESKey) {
        this.cipheredAESKey = cipheredAESKey;
    }

    public byte[] getSealedMessage() {
        return sealedMessage;
    }

    public void setSealedMessage(byte[] sealedMessage) {
        this.sealedMessage = sealedMessage;
    }

    public String getClientPublicKey() {
        return clientPublicKey;
    }

    public void setClientPublicKey(String clientPublicKey) {
        this.clientPublicKey = clientPublicKey;
    }
}
