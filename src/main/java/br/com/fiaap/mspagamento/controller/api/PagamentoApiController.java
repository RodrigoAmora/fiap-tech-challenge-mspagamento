package br.com.fiaap.mspagamento.controller.api;

import br.com.fiaap.mspagamento.controller.api.doc.PagamentoApiDoc;
import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import br.com.fiaap.mspagamento.service.PagamentoService;
import org.springframework.data.domain.Page;
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
    @GetMapping
    public ResponseEntity<Page<Pagamento>> listarPagamentos(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                            @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return ResponseEntity.ok(pagamentoService.listarPagamentos(page, size));
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
    @PutMapping(value = {"/{idPagamento}/atualizarSatus"})
    public ResponseEntity<Pagamento> atualizarSatus(@PathVariable(name = "idPagamento") String idPagamento) {
        return ResponseEntity.ok(pagamentoService.atualizarSatus(idPagamento));
    }

    @Override
    @PostMapping(value = {"/{idPagamento}/aprovar"})
    public ResponseEntity<Pagamento> aprovarPagamento(@PathVariable(name = "idPagamento") String idPagamento) {
        return ResponseEntity.ok(pagamentoService.aprovarPagamento(idPagamento));
    }

    @Override
    @PostMapping(value = {"/{idPagamento}/rejeitar"})
    public ResponseEntity<Pagamento> rejeitarPagamento(@PathVariable(name = "idPagamento") String idPagamento) {
        return ResponseEntity.ok(pagamentoService.rejeitarPagamento(idPagamento));
    }

    @Override
    @PostMapping(value = {"/{idPagamento}/cancelar"})
    public ResponseEntity<Pagamento> cancelarPagamento(@PathVariable(name = "idPagamento") String idPagamento) {
        return ResponseEntity.ok(pagamentoService.cancelarPagamento(idPagamento));
    }

    @Override
    @GetMapping("/{idCliente}/cliente")
    public ResponseEntity<Page<Pagamento>> buscarPagamentosDoCliente(@PathVariable(name = "idCliente") String idCliente,
                                                                     @RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                                     @RequestParam(value = "size", required = false, defaultValue = "20") int size) {
        return ResponseEntity.ok(pagamentoService.buscarPagamentosDoCliente(idCliente, page, size));
    }

}
