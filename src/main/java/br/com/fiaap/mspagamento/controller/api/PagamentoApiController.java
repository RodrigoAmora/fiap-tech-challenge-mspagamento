package br.com.fiaap.mspagamento.controller.api;

import br.com.fiaap.mspagamento.controller.doc.PagamentoApiDoc;
import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import br.com.fiaap.mspagamento.service.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoApiController implements PagamentoApiDoc {

    private final PagamentoService pagamentoService;

    public PagamentoApiController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    @PostMapping
    public ResponseEntity<Pagamento> realizarPagamento(@RequestBody PagamentoRequest request) {
        return ResponseEntity.ok(pagamentoService.realizarPagamento(request));
    }

}
