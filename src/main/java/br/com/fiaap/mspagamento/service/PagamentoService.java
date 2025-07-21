package br.com.fiaap.mspagamento.service;

import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.StatusPagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import br.com.fiaap.mspagamento.repository.PagamentoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepository;

    public PagamentoService(PagamentoRepository pagamentoRepository) {
        this.pagamentoRepository = pagamentoRepository;
    }

    public Pagamento realizarPagamento(PagamentoRequest request) {
        return pagamentoRepository.save(request.mapPagamento());
    }

    public Pagamento buscarPedidoPeloId(String idPagamento) {
        Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(idPagamento);
        if (pagamentoOptional.isPresent()) {
            return pagamentoOptional.get();
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado com o ID: " + idPagamento);
    }

    public Pagamento atualizarSatus(String idPagamento) {
        Optional<Pagamento> pagamentoOptional = pagamentoRepository.findById(idPagamento);
        if (pagamentoOptional.isPresent()) {
            Pagamento pagamento = pagamentoOptional.get();
            switch (pagamento.getStatusPagamento()) {
                case PENDENTE -> {
                    pagamento.setStatusPagamento(StatusPagamento.PROCESSANDO);
                }
                case PROCESSANDO -> {
                    pagamento.setStatusPagamento(StatusPagamento.APROVADO);
                }
            }
            return pagamentoRepository.save(pagamento);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pagamento não encontrado com o ID: " + idPagamento);
    }

}
