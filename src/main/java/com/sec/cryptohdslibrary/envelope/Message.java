package com.sec.cryptohdslibrary.envelope;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;
import com.sec.cryptohdslibrary.security.CipherInstance;
import com.sec.cryptohdslibrary.security.DigSignature;
import com.sec.cryptohdslibrary.service.dto.CryptohdsDTO;
import com.sec.cryptohdslibrary.service.dto.LedgerDTO;
import com.sec.cryptohdslibrary.util.Util;

import java.io.Serializable;


public class Message implements Serializable {

    private byte[] signedContent;

    private CryptohdsDTO content;
    
    private int seqNumber;

    public Message() {}

    public Message(CryptohdsDTO ledgerDTO, KeyStoreImpl clientKeyStore) { //FIXME vai ter que receber aqui o seqNumber

        /*Store on the Envelope the Original Message. To later verify if Signature is Authentic*/
        this.content = ledgerDTO;

        /*Sign the Message itself, and store it on the Envelope*/
        this.signedContent = DigSignature.signMessage(Util.objectToByte(ledgerDTO), clientKeyStore.getkeyPairHDS().getPrivate());
    }

    public boolean verifyMessageSignature(String clientPublickKey) {
        return DigSignature.verifyMessageSignature(CipherInstance.decodePublicKey(clientPublickKey), signedContent, Util.objectToByte(content));
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
    
    public int getSeqNumb() {
    	return seqNumber;
    }
    
    public void setSeqNumber(int seqNumb) {
    	seqNumber = seqNumb;
    }
    
}
