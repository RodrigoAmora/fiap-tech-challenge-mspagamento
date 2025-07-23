package br.com.fiaap.mspagamento.service;

import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.StatusPagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import br.com.fiaap.mspagamento.repository.PagamentoRepository;
import br.com.fiaap.mspagamento.validator.CartaoValidator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento realizarPagamento(PagamentoRequest request) {
        CartaoValidator.validarCartao(request.cartao());
        return pagamentoRepository.save(request.mapPagamento());
    }

    public Pagamento buscarPedidoPeloId(String idPagamento) {
        return buscarPagamentoPeloIdOuLancarExcecao(idPagamento);
    }

    public Pagamento atualizarSatus(String idPagamento) {
        Pagamento pagamento = buscarPagamentoPeloIdOuLancarExcecao(idPagamento);
        switch (pagamento.getStatusPagamento()) {
            case PENDENTE -> {
                return atualizarSatusPagamento(pagamento, StatusPagamento.PROCESSANDO);
            }
            case PROCESSANDO -> {
                return atualizarSatusPagamento(pagamento, StatusPagamento.APROVADO);
            }
            default -> {
                return pagamento;
            }
        }
    }

    public Pagamento aprovarPagamento(String idPagamento) {
        Pagamento pagamento = buscarPagamentoPeloIdOuLancarExcecao(idPagamento);
        return atualizarSatusPagamento(pagamento, StatusPagamento.APROVADO);
    }

    public Pagamento rejeitarPagamento(String idPagamento) {
        Pagamento pagamento = buscarPagamentoPeloIdOuLancarExcecao(idPagamento);
        return atualizarSatusPagamento(pagamento, StatusPagamento.REJEITADO);
    }

    public Pagamento cancelarPagamento(String idPagamento) {
        Pagamento pagamento = buscarPagamentoPeloIdOuLancarExcecao(idPagamento);
        return atualizarSatusPagamento(pagamento, StatusPagamento.CANCELADO);
    }

    private Pagamento buscarPagamentoPeloIdOuLancarExcecao(String idPagamento) {
        return pagamentoRepository.findById(idPagamento)
                                    .orElseThrow(() -> new ResponseStatusException(
                                            HttpStatus.NOT_FOUND,
                                            "Pagamento não encontrado com o ID: " + idPagamento
                                    ));
    }

    public Page<Pagamento> buscarPagamentosDoCliente(String idCliente, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return pagamentoRepository.findByIdCliente(idCliente, pageRequest);
    }

    private Pagamento atualizarSatusPagamento(Pagamento pagamento, StatusPagamento statusPagamento) {
        pagamento.setAtualizacaoStatus(LocalDateTime.now());
        pagamento.setStatusPagamento(statusPagamento);
        return pagamentoRepository.save(pagamento);
    }

}
