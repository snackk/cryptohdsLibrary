package com.sec.cryptohdslibrary.service.dto;

import java.io.Serializable;

public class ReceiveOperationDTO implements Serializable {

    private String publicKey;

    private Long operationId;

    public ReceiveOperationDTO() {}

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }
}
