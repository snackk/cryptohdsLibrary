package com.sec.cryptohdslibrary.envelope;

import com.sec.cryptohdslibrary.keystore.KeyStoreImpl;
import com.sec.cryptohdslibrary.security.DigSignature;
import com.sec.cryptohdslibrary.security.RsaRelatedMethods;
import com.sec.cryptohdslibrary.service.dto.LedgerDTO;
import com.sec.cryptohdslibrary.util.Util;

import java.io.Serializable;

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

    public String getCipheredEnvelope(String cryptoHdsPKey) {

        /*Cipher the Envelop(this) with the Public Key of the Server(cryptoHdsPKey)*/
        return RsaRelatedMethods.bytesToString(RsaRelatedMethods.RSACipher(Util.objectToByte(this), RsaRelatedMethods.decodePublicKey(cryptoHdsPKey)));
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
