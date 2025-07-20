package br.com.fiaap.mspagamento.domain.dto.request;

import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.StatusPagamento;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PagamentoRequest(

        @JsonProperty("id_pedido")
        UUID idPedido,

        @JsonProperty("id_cliente")
        UUID idCliente,

        @JsonProperty("total")
        BigDecimal valorTotal
) {
        public Pagamento mapPagamento() {
                Pagamento pagamento = new Pagamento();
                pagamento.setDataPagamento(LocalDateTime.now());
                pagamento.setIdCliente(idCliente);
                pagamento.setIdPedido(idPedido);
                pagamento.setStatusPagamento(StatusPagamento.RECEBIDO);
                pagamento.setValorTotal(valorTotal);
                return pagamento;
        }
}
