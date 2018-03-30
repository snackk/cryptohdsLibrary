package com.sec.cryptohdslibrary.service.dto;

import java.io.Serializable;
import java.util.List;

public class LedgerBalanceDTO  implements Serializable {

    private Long balance;

    private List<OperationDTO> pendingOperations;

    public LedgerBalanceDTO(Long balance, List<OperationDTO> pendingOperations) {
        this.balance = balance;
        this.pendingOperations = pendingOperations;
    }

    public LedgerBalanceDTO() {}

    public List<OperationDTO> getPendingOperations() {
        return pendingOperations;
    }

    public void setPendingOperations(List<OperationDTO> pendingOperations) {
        this.pendingOperations = pendingOperations;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }
}
