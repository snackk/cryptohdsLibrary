package com.sec.cryptohdslibrary.service.dto;

public class ReceiveOperationDTO extends CryptohdsDTO {

	private static final long serialVersionUID = -3380854167931395053L;

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
