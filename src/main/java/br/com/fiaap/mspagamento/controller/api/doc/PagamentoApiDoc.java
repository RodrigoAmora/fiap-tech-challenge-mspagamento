package br.com.fiaap.mspagamento.controller.api.doc;

import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

@Tag(name = "Endpoints de pagamento")
public interface PagamentoApiDoc {

    @Operation(summary = "Listar pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listar todos os pagamentos realizados.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Page<Pagamento>> listarPagamentos(int page, int size);

    @Operation(summary = "Realização de pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Realização de pagamento de um pedido.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> realizarPagamento(PagamentoRequest request);

    @Operation(summary = "Busca do pagamento pelo id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca do pagamento pelo id.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> buscarPedidoPeloId(String idPagamento);

    @Operation(summary = "Atualização do status de pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Atualização do status de pagamento pelo id.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> atualizarSatus(String idPagamento);

    @Operation(summary = "Aprpvar pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aprpvar o pagamento passandoo id.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> aprovarPagamento(@PathVariable(name = "idPagamento") String idPagamento);

    @Operation(summary = "Rejeitar pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Rejeitar o pagamento passandoo id.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> rejeitarPagamento(String idPagamento);

    @Operation(summary = "Cancelar pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cancelar o pagamento passandoo id.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> cancelarPagamento(String idPagamento);

    @Operation(summary = "Pagamentos do cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Buscar todos os pagamento de um cliente.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Page<Pagamento>> buscarPagamentosDoCliente(String idCliente, int page, int size);
}
