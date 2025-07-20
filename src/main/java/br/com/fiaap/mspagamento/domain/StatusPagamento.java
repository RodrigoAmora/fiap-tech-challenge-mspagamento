package br.com.fiaap.mspagamento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPagamento {

    RECEBIDO("RECEBIDO"),
    CONFIRMADO("CONFIRMADO"),
    NEGADO("NEGADO"),
    CANCELADO("CANCELADO");

    private String status;

    StatusPagamento(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
