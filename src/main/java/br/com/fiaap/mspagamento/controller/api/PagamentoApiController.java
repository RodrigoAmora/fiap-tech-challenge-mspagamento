package br.com.fiaap.mspagamento.controller.api;

import br.com.fiaap.mspagamento.controller.api.doc.PagamentoApiDoc;
import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import br.com.fiaap.mspagamento.service.PagamentoService;
import io.swagger.v3.oas.annotations.headers.Header;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pagamento")
public class PagamentoApiController implements PagamentoApiDoc {

    private final PagamentoService pagamentoService;

    public PagamentoApiController(PagamentoService pagamentoService) {
        this.pagamentoService = pagamentoService;
    }

    @Override
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pagamento> realizarPagamento(@RequestBody PagamentoRequest request) {
        return ResponseEntity.ok(pagamentoService.realizarPagamento(request));
    }

    @Override
    @GetMapping(value = {"/{idPagamento}"})
    public ResponseEntity<Pagamento> buscarPedidoPeloId(@PathVariable(name = "idPagamento") String idPagamento) {
        return ResponseEntity.ok(pagamentoService.buscarPedidoPeloId(idPagamento));
    }

    @Override
    @PutMapping(value = {"/{idPagamento}"})
    public ResponseEntity<Pagamento> atualizarSatus(@PathVariable(name = "idPagamento") String idPagamento) {
        return ResponseEntity.ok(pagamentoService.atualizarSatus(idPagamento));
    }

}
