package com.sec.cryptohdslibrary.keystore;

import com.sec.cryptohdslibrary.security.DigCertificate;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class KeyStoreImpl {

    private String alias;

    private String password;

    private KeyPair keyPairHDS;

    private final String keyStoreLocation = "KeyStore/";

    public KeyStoreImpl(String alias, String password) {
        this.alias = alias;
        this.password = password;

        generateKeyStore();
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public KeyPair getkeyPairHDS() {
        return keyPairHDS;
    }

    public void setkeyPairHDS(KeyPair keyPair) {
        this.keyPairHDS = keyPair;
    }

    private boolean keyStoreExists(){
        File file = new File(getKeyStorePath());
        return file.exists() && file.isFile();
    }

    private String getKeyStorePath(){
        return this.keyStoreLocation + getAlias() + ".jce";
    }

    private String getPublicKeyPath(){
        return this.keyStoreLocation + getAlias() + ".pubKey";
    }

    private String getPrivateKeyPath(){
        return this.keyStoreLocation + getAlias() + ".pKey";
    }

    private void generateKeyStore() {
        KeyStore keyStore = null;

        if (!keyStoreExists()){
            File file = new File(getKeyStorePath());

            // create parent directories for key storage
            file.getParentFile().mkdirs();

            this.setkeyPairHDS(generateKeyPair());
            keyStore = generateKeyStore(this.getkeyPairHDS());
            storeKeyStore(keyStore);
        } else {
            this.setkeyPairHDS(getKeyPair());
        }
    }

    private KeyStore getKeyStore() {
        try {
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            FileInputStream fis = new FileInputStream(getKeyStorePath());
            keyStore.load(fis, getPassword().toCharArray());
            fis.close();
            return keyStore;

        } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            return null;
        }
    }

    private KeyPair getKeyPair() {
        try {
            Key pKey = getKeyStore().getKey("PrivateKey", getPassword().toCharArray());

            if (pKey instanceof PrivateKey) {
                Certificate cert = getKeyStore().getCertificate("PrivateKey");
                return new KeyPair(cert.getPublicKey(), (PrivateKey) pKey);
            }
            return null;

        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException e){
            e.printStackTrace();
            return null;
        }
    }

    public static KeyPair generateKeyPair() {
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
            keyStore.load(null, getPassword().toCharArray());

        } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
            e.printStackTrace();
        }

        X509Certificate[] certificate = null;
        try {
            certificate = DigCertificate.generateCertificate(keyPair);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Certificate[] certChain = new Certificate[1];
        certChain[0] = certificate[0];
        try {
            keyStore.setKeyEntry("PrivateKey", (Key) keyPair.getPrivate(), getPassword().toCharArray(), certChain);

        } catch ( KeyStoreException e) {
            e.printStackTrace();
        }

        return keyStore;
    }

    private void storeKeyStore(KeyStore keyStore) {
        try {
            FileOutputStream fos = new FileOutputStream(getKeyStorePath());
            keyStore.store(fos, getPassword().toCharArray());
            fos.close();

        } catch (IOException | KeyStoreException | NoSuchAlgorithmException | CertificateException e) {
            e.printStackTrace();
            return;
        }
    }
}

