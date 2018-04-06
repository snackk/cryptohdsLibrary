package com.sec.cryptohdslibrary.service.dto;

import java.util.ArrayList;
import java.util.List;

public class LedgerDTO extends CryptohdsDTO {

    private Long id;

    private String name;

    private String publicKey;

    private Long balance;
    
    private int seqNumber;

    private List<OperationDTO> operations = new ArrayList<>();

    public LedgerDTO() {}

    public LedgerDTO(String publicKey) {
        this.publicKey = publicKey;
    }

    public LedgerDTO(String name, String publicKey) {
        this.publicKey = publicKey;
        this.name = name;
        this.balance = 90L;
        this.seqNumber = -1;
    }
    
    public int getSeqNumber() {
    	return seqNumber;
    }
   
    public void setSeqNumber(int seqNumb) {
    	seqNumber = seqNumb;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public List<OperationDTO> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationDTO> operations) {
        this.operations = operations;
    }
}
