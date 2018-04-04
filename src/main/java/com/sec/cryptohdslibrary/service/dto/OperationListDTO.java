package com.sec.cryptohdslibrary.service.dto;

import java.util.List;

public class OperationListDTO extends CryptohdsDTO {


    private List<OperationDTO> pendingOperations;

    public OperationListDTO(List<OperationDTO> pendingOperations) {
        this.pendingOperations = pendingOperations;
    }

    public OperationListDTO() {}

    public List<OperationDTO> getPendingOperations() {
        return pendingOperations;
    }

    public void setPendingOperations(List<OperationDTO> pendingOperations) {
        this.pendingOperations = pendingOperations;
    }
    
    

}
