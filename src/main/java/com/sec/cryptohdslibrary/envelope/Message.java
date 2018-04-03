package com.sec.cryptohdslibrary.envelope;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;
import com.sec.cryptohdslibrary.security.CipherInstance;
import com.sec.cryptohdslibrary.security.DigSignature;
import com.sec.cryptohdslibrary.service.dto.LedgerDTO;
import com.sec.cryptohdslibrary.util.Util;

import java.io.Serializable;


public class Message implements Serializable {

    private byte[] signedContent;

    private LedgerDTO content;

    public Message() {}

    public Message(String ledgerName, KeyStoreImpl clientKeyStore) {

        /*Create LedgerDTO with his public key encode as base64*/
        LedgerDTO ledgerDTO = new LedgerDTO(ledgerName, CipherInstance.encodePublicKey(clientKeyStore.getkeyPairHDS().getPublic()));

        /*Store on the Envelope the Original Message. To later verify if Signature is Authentic*/
        this.content = ledgerDTO;

        /*Sign the Message itself, and store it on the Envelope*/
        this.signedContent = DigSignature.signMessage(Util.objectToByte(ledgerDTO), clientKeyStore.getkeyPairHDS().getPrivate());
    }

    public byte[] getSignedContent() {
        return signedContent;
    }

    public void setSignedContent(byte[] signedContent) {
        this.signedContent = signedContent;
    }

    public LedgerDTO getContent() {
        return content;
    }

    public void setContent(LedgerDTO content) {
        this.content = content;
    }
}
