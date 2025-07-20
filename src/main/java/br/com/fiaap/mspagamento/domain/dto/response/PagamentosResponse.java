package br.com.fiaap.mspagamento.domain.dto.response;

import br.com.fiaap.mspagamento.domain.Pagamento;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(
        name = "PagamentoResponse",
        description = "Resposta após processamento de um pagamento"
)
public record PagamentosResponse(
        List<Pagamento> pagamentos
) {}
