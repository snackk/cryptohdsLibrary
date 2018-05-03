package com.sec.cryptohdslibrary.service.dto;

import java.util.List;

public class OperationListDTO extends CryptohdsDTO {

	private static final long serialVersionUID = -2812788279011207746L;

	private List<OperationDTO> pendingOperations;

    public OperationListDTO() {

    }

    public OperationListDTO(List<OperationDTO> pendingOperations) {
        this.pendingOperations = pendingOperations;
    }

    public List<OperationDTO> getPendingOperations() {
        return pendingOperations;
    }

    public void setPendingOperations(List<OperationDTO> pendingOperations) {
        this.pendingOperations = pendingOperations;
    }
}
