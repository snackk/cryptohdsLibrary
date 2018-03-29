package com.sec.cryptohdslibrary.security;

import sun.security.x509.AlgorithmId;
import sun.security.x509.CertificateAlgorithmId;
import sun.security.x509.CertificateSerialNumber;
import sun.security.x509.CertificateX509Key;
import sun.security.x509.CertificateValidity;
import sun.security.x509.CertificateVersion;
import sun.security.x509.X509CertInfo;
import sun.security.x509.X500Name;
import sun.security.x509.X509CertImpl;

import java.util.Date;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;


public class DigCertificate {

    public static X509Certificate[] generateCertificate(KeyPair keyPair) throws Exception {

        X509CertInfo certInfo = new X509CertInfo();
        Date from = new Date();
        Date to = new Date(from.getTime() + 365 * 86400000l);

        CertificateValidity interval = new CertificateValidity(from, to);
        X500Name owner = new X500Name("C=PT, ST=cryptohds");

        certInfo.set(X509CertInfo.SERIAL_NUMBER, new CertificateSerialNumber(new BigInteger(64, new SecureRandom())));
        certInfo.set(X509CertInfo.KEY, new CertificateX509Key(keyPair.getPublic()));
        certInfo.set(X509CertInfo.VERSION, new CertificateVersion(CertificateVersion.V3));
        certInfo.set(X509CertInfo.VALIDITY, interval);
        certInfo.set(X509CertInfo.SUBJECT, owner);
        certInfo.set(X509CertInfo.ISSUER,owner);

        AlgorithmId md5WRsa = new AlgorithmId(AlgorithmId.md5WithRSAEncryption_oid);
        certInfo.set(X509CertInfo.ALGORITHM_ID, new CertificateAlgorithmId(md5WRsa));

        X509CertImpl generatedCertificate = new X509CertImpl(certInfo);
        generatedCertificate.sign(keyPair.getPrivate(), "SHA1withRSA");
        md5WRsa = (AlgorithmId)generatedCertificate.get(X509CertImpl.SIG_ALG);
        certInfo.set(CertificateAlgorithmId.NAME + "." + CertificateAlgorithmId.ALGORITHM, md5WRsa);
        generatedCertificate = new X509CertImpl(certInfo);
        generatedCertificate.sign(keyPair.getPrivate(), "SHA1withRSA");

        return new X509Certificate[]{generatedCertificate};
    }
}
