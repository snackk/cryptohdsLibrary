package com.sec.cryptohdslibrary.envelope;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;
import com.sec.cryptohdslibrary.security.CipherInstance;
import com.sec.cryptohdslibrary.security.DigSignature;
import com.sec.cryptohdslibrary.service.dto.CryptohdsDTO;
import com.sec.cryptohdslibrary.util.Util;

import java.io.Serializable;


public class Message implements Serializable {

    private byte[] signedContent;

    private CryptohdsDTO content;
    
    private int seqNumber;

    public Message() {}

    /*Constructor of Message with Content*/
    public Message(CryptohdsDTO ledgerDTO, KeyStoreImpl clientKeyStore, int seqNumber) { //FIXME vai ter que receber aqui o seqNumber

        /*Build the Message with the given seqNumber*/
        this.seqNumber = seqNumber;

        /*Store on the Envelope the Original Message. To later verify if Signature is Authentic*/
        this.content = ledgerDTO;

        /*Sign the Message itself, and store it on the Envelope*/
        this.signedContent = DigSignature.signMessage(Util.objectToByte(ledgerDTO), clientKeyStore.getkeyPairHDS().getPrivate());
    }

    public boolean verifyMessageSignature(String clientPublicKey) {
        return DigSignature.verifyMessageSignature(CipherInstance.decodePublicKey(clientPublicKey), signedContent, Util.objectToByte(content));
    }

    public byte[] getSignedContent() {
        return signedContent;
    }

    public void setSignedContent(byte[] signedContent) {
        this.signedContent = signedContent;
    }

    public CryptohdsDTO getContent() {
        return content;
    }

    public void setContent(CryptohdsDTO content) {
        this.content = content;
    }


    public int getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(int seqNumber) {
        this.seqNumber = seqNumber;
    }
}
