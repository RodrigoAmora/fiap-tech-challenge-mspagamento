package br.com.fiaap.mspagamento.domain.dto;

import br.com.fiaap.mspagamento.domain.Pagamento;

import java.util.List;

public record PagamentosResponse(
        List<Pagamento> pagamentos
) {}
