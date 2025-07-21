package br.com.fiaap.mspagamento.controller.api.doc;

import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "Endpoints de pagamento")
public interface PagamentoApiDoc {

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
            @ApiResponse(responseCode = "200", description = "Atualização do  status de pagamento pelo id.", content = @Content(schema = @Schema(implementation = Pagamento.class))),
    })
    ResponseEntity<Pagamento> atualizarSatus(String idPagamento);
}
