package br.com.fiaap.mspagamento.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "pagamentos")
@Data
public class Pagamento {

        @JsonProperty("id")
        UUID idPagamento;

        @JsonProperty("id_pedido")
        UUID idPedido;

        @JsonProperty("id_cliente")
        UUID idCliente;

        @JsonProperty("total")
        BigDecimal valorTotal;

        @Enumerated(EnumType.STRING)
        @JsonProperty("status")
        StatusPagamento statusPagamento;

        @JsonProperty("data")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime dataPagamento;

}
