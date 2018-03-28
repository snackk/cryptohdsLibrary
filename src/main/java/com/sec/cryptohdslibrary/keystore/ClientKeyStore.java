package com.sec.cryptohdslibrary.keystore;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SecureRandom;

import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class ClientKeyStore {

    private String ledgerName;

    private String ledgerPassword;

    private KeyPair ledgerKeyPair;

    public ClientKeyStore() {
    }

    private String getKeyStoreFile(){
        return "keys/" + getLedgerName() + ".jce";
    }

    private String getPubKeyFile(){
        return "keys/" + getLedgerName() + ".pubKey";
    }

    private String getPrivateKeyFile(){
        return "keys/" + getLedgerName() + ".pKey";
    }

    private boolean keyStoreExists(){
        return new File(getKeyStoreFile()).isFile();
    }

    public String getLedgerName() {
        return this.ledgerName;
    }

    private void setLedgerName(String ledgerName) {
        this.ledgerName = ledgerName;
    }

    public String getLedgerPassword() {
        return ledgerPassword;
    }

    public void setLedgerPassword(String ledgerPassword) {
        this.ledgerPassword = ledgerPassword;
    }

    public void generateKeyStore(String ledgerName, String ledgerPassword) {
        this.setLedgerName(ledgerName);
        this.setLedgerPassword(ledgerPassword);

        KeyStore keyStore = null;
        if (keyStoreExists()){
            keyStore = getKeyStore();
            this.ledgerKeyPair = getKeyPair(keyStore);
        } else {
            this.ledgerKeyPair = generateKeyPair();
            keyStore = generateKeyStore(this.ledgerKeyPair);
            storeKeyStore(keyStore);
        }
    }

    private KeyStore getKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream fis = new FileInputStream(getKeyStoreFile());
            keyStore.load(fis, ledgerPassword.toCharArray());
            fis.close();
            return keyStore;

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    private KeyPair getKeyPair(KeyStore keyStore) {
        try {
            Key pKey = keyStore.getKey("PrivateKey", this.ledgerPassword.toCharArray());

            if (pKey instanceof PrivateKey) {
                Certificate cert = keyStore.getCertificate("PrivateKey");
                return new KeyPair(cert.getPublicKey(), (PrivateKey) pKey);
            }
            return null;

        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e){
            e.printStackTrace();
            return null;
        }
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            keyGen.initialize(1024, random);
            KeyPair keyPair = keyGen.generateKeyPair();
            return keyPair;

        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

    private KeyStore generateKeyStore(KeyPair keyPair){
        KeyStore keyStore = null;
        try {
            keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, this.ledgerPassword.toCharArray());
        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }

        X509Certificate[] certificate = null;
        try {
            certificate = com.sec.cryptohdslibrary.security.Certificate.generateCert(keyPair);
        } catch ( Exception e) {
            e.printStackTrace();
        }

        Certificate[] certChain = new Certificate[1];
        certChain[0] = certificate[0];
        try {
            keyStore.setKeyEntry("PrivateKey", (Key)keyPair.getPrivate(), this.ledgerPassword.toCharArray(), certChain);
        } catch ( KeyStoreException e) {
            e.printStackTrace();
        }

        return keyStore;
    }

    private void storeKeyStore(KeyStore ks) {
        try {
            FileOutputStream fos = new FileOutputStream(getKeyStoreFile());
            ks.store(fos, this.ledgerPassword.toCharArray());
            fos.close();
        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
        }
    }
}

