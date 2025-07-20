package br.com.fiaap.mspagamento.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.UUID;

public record PagamentoRequest(
        @JsonProperty("id")
        UUID idPagamento,

        @JsonProperty("id_pedido")
        UUID idPedido,

        @JsonProperty("id_cliente")
        UUID idCliente,

        @JsonProperty("total")
        BigDecimal valorTotal
) {}
