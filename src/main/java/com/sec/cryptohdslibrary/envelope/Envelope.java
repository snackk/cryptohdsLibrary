package com.sec.cryptohdslibrary.envelope;

import com.sec.cryptohdslibrary.security.CipherInstance;
import com.sec.cryptohdslibrary.util.Util;

import java.io.Serializable;

import javax.crypto.SealedObject;
import javax.crypto.SecretKey;


public class Envelope implements Serializable {

    private String cipheredAESKey;

    private SealedObject sealedMessage;

    public Envelope() {}

    public void cipherEnvelope(Message message, String cryptoHdsPKey) {

        /*Generate AES key*/
        SecretKey aesKey = CipherInstance.generateAESKey();

        /*Cipher AES key with public key of Server and store it*/
        this.cipheredAESKey = Util.bytesToString(CipherInstance.RSACipher(aesKey.getEncoded(),
                CipherInstance.decodePublicKey(cryptoHdsPKey)));

        /*Generate a sealed message with AES key and store it*/
        this.sealedMessage = CipherInstance.AESCipherMessage(message, aesKey);
    }

    public String getCipheredAESKey() {
        return cipheredAESKey;
    }

    public void setCipheredAESKey(String cipheredAESKey) {
        this.cipheredAESKey = cipheredAESKey;
    }

    public SealedObject getSealedMessage() {
        return sealedMessage;
    }

    public void setSealedMessage(SealedObject sealedMessage) {
        this.sealedMessage = sealedMessage;
    }
}
