package br.com.fiaap.mspagamento.domain.dto.request;

import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Schema(
        name = "PagamentoRequest",
        description = "Dados necessários para realizar um pagamento"
)
public record PagamentoRequest(

        @Schema(
                description = "Identificador único do pedido",
                example = "abf3a673-5211-483d-a25e-ce060baed2a0"
        )
        @JsonProperty("id_pedido")
        UUID idPedido,

        @Schema(
                description = "Identificador único do cliente",
                example = "1a3f0c88-7680-4c13-a439-802ca48f377c"
        )
        @JsonProperty("id_cliente")
        UUID idCliente,

        @Schema(
                description = "Valor do pagamento",
                example = "159.90",
                minimum = "0.01",
                required = true
        )
        @JsonProperty("total")
        BigDecimal valorTotal
) {
        public Pagamento mapPagamento() {
                Pagamento pagamento = new Pagamento();
                pagamento.setDataPagamento(LocalDateTime.now());
                pagamento.setIdCliente(idCliente);
                pagamento.setIdPedido(idPedido);
                pagamento.setStatusPagamento(StatusPagamento.PENDENTE);
                pagamento.setValorTotal(valorTotal);
                return pagamento;
        }
}
