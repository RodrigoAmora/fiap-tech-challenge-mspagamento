package br.com.fiaap.mspagamento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Status possíveis de um pagamento")
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum StatusPagamento {

    @Schema(description = "Pagamento aprovado com sucesso")
    APROVADO("APROVADO"),

    @Schema(description = "Pagamento em processamento")
    PROCESSANDO("PROCESSANDO"),

    @Schema(description = "Pagamento ainda não processado")
    PENDENTE("PENDENTE"),

    @Schema(description = "Pagamento foi rejeitado")
    REJEITADO("REJEITADO");

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
