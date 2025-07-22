package br.com.fiaap.mspagamento.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class Cartao {

    @Schema(
            description = "Número do Cartão",
            example = "5486416717179402"
    )
    @JsonProperty("numero_cartao")
    private String numeroCartao;

    @Schema(
            description = "Nome do Titular",
            example = "Fulano da Silva"
    )
    @JsonProperty("nome_titular")
    private String nomeTitular;

    @Schema(
            description = "Data de Validade",
            example = "07/27"
    )
    @JsonProperty("data_validade")
    private String dataValidade;

    @Schema(
            description = "CVV",
            example = "276"
    )
    @JsonProperty("cvv")
    private String codigoSeguranca;

    @Schema(
            description = "Bandeira do Cartão",
            example = "VISA"
    )
    @JsonProperty("bandeira")
    private String bandeira;

}
