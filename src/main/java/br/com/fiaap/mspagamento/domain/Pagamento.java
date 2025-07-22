package br.com.fiaap.mspagamento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "pagamentos")
@Data
public class Pagamento {

        @Id
        @JsonProperty("id")
        private String idPagamento;

        @JsonProperty("id_pedido")
        private UUID idPedido;

        @JsonProperty("id_cliente")
        private UUID idCliente;

        @JsonProperty("total")
        private BigDecimal valorTotal;

        @JsonProperty("status")
        private StatusPagamento statusPagamento;

        @JsonProperty("data")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime dataPagamento;

}
