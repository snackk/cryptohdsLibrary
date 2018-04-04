package com.sec.cryptohdslibrary.service.dto;

import java.time.LocalDateTime;

public class OperationDTO extends CryptohdsDTO {

    private Long id;

    private LocalDateTime timestamp;

    private Long value;

    private Boolean committed;

    private String type;

    private String originPublicKey;

    private String destinationPublicKey;

    public OperationDTO() {}

    public OperationDTO(Long id, LocalDateTime timestamp, Long value, Boolean committed, String type, String originPublicKey, String destinationPublicKey) {
        this.id = id;
        this.timestamp = timestamp;
        this.committed = committed;
        this.type = type;
        this.originPublicKey = originPublicKey;
        this.destinationPublicKey = destinationPublicKey;
        this.value = value;
    }

    public OperationDTO(String originPublicKey, String destinationPublicKey, Long value) {
        this.originPublicKey = originPublicKey;
        this.destinationPublicKey = destinationPublicKey;
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public Boolean getCommitted() {
        return committed;
    }

    public void setCommitted(Boolean committed) {
        this.committed = committed;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOriginPublicKey() {
        return originPublicKey;
    }

    public void setOriginPublicKey(String originPublicKey) {
        this.originPublicKey = originPublicKey;
    }

    public String getDestinationPublicKey() {
        return destinationPublicKey;
    }

    public void setDestinationPublicKey(String destinationPublicKey) {
        this.destinationPublicKey = destinationPublicKey;
    }
}
