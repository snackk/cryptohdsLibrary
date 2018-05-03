package com.sec.cryptohdslibrary.service.dto;

import java.time.LocalDateTime;

public class OperationDTO extends CryptohdsDTO {

	private static final long serialVersionUID = -5413133358084845758L;

	private Long id;

    private LocalDateTime timestamp;

    private Long value;

    private Boolean committed;

    private String originPublicKey;

    private String destinationPublicKey;

    public OperationDTO() {}

    public OperationDTO(Long id, LocalDateTime timestamp, Long value, Boolean committed, String originPublicKey, String destinationPublicKey) {
        this.id = id;
        this.timestamp = timestamp;
        this.committed = committed;
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

    @Override
    public String toString() {

        return "id: " + id + "\n" +
                "timestamp: " + timestamp.toString() + "\n" +
                "value: " + value + "\n" +
                "committed: " + committed + "\n" +
                "origin: " + originPublicKey + "\n" +
                "destination: " + destinationPublicKey + "\n";
    }
}