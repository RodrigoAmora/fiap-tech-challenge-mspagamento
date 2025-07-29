package br.com.fiaap.mspagamento.service;

import br.com.fiaap.mspagamento.domain.Cartao;
import br.com.fiaap.mspagamento.domain.Pagamento;
import br.com.fiaap.mspagamento.domain.StatusPagamento;
import br.com.fiaap.mspagamento.domain.dto.request.PagamentoRequest;
import br.com.fiaap.mspagamento.exception.CartaoInvalidoException;
import br.com.fiaap.mspagamento.repository.PagamentoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
class PagamentoServiceTest {

    @InjectMocks
    private PagamentoService pagamentoService;

    @Mock
    private PagamentoRepository pagamentoRepository;

    private PagamentoRequest requestValido;
    private PagamentoRequest requestInvalido;
    private Pagamento pagamentoEsperado;

    @BeforeEach
    public void init() {
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("276");
        cartao.setDataValidade("07/27");
        cartao.setNumeroCartao("5486416717179402");
        cartao.setNomeTitular("Fulano da Silva");

        requestValido = new PagamentoRequest("6af23584-77c6-4107-a6aa-41d01c86727f",
                "89a3d9e0-0c9f-48bf-a7f8-579ee94bca07",
                cartao,
                new BigDecimal(20.90)
                );

        pagamentoEsperado = requestValido.mapPagamento();

        // Configurando cartão inválido
        Cartao cartaoInvalido = new Cartao();
        cartaoInvalido.setBandeira("VISA");
        cartaoInvalido.setCodigoSeguranca("000");
        cartaoInvalido.setDataValidade("01/20"); // Data expirada
        cartaoInvalido.setNumeroCartao("1234567890123456"); // Número inválido
        cartaoInvalido.setNomeTitular("");

        requestInvalido = new PagamentoRequest(
                "6af23584-77c6-4107-a6aa-41d01c86727f",
                "89a3d9e0-0c9f-48bf-a7f8-579ee94bca07",
                cartaoInvalido,
                new BigDecimal("20.90")
        );

    }

    @Test
    void realizarPagamento_QuandoDadosValidos_DeveRetornarPagamentoSalvo() {
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoEsperado);

        Pagamento resultado = pagamentoService.realizarPagamento(requestValido);

        assertNotNull(resultado);
        assertEquals(requestValido.idPedido(), resultado.getIdPedido());
        assertEquals(requestValido.idCliente(), resultado.getIdCliente());
        assertEquals(requestValido.valorTotal(), resultado.getValorTotal());
        assertEquals(StatusPagamento.PENDENTE, resultado.getStatusPagamento());
        verify(pagamentoRepository, times(1)).save(any(Pagamento.class));
    }

    @Test
    void realizarPagamento_QuandoCartaoInvalido_DeveLancarExcecao() {
        assertThrows(CartaoInvalidoException.class, () -> {
            pagamentoService.realizarPagamento(requestInvalido);
        });

        verify(pagamentoRepository, never()).save(any(Pagamento.class));
    }

    @Test
    void buscarPedidoPeloId_QuandoIdValido_DeveRetornarPagamento() {
        when(pagamentoRepository.findById(pagamentoEsperado.getIdPagamento())).thenReturn(Optional.of(pagamentoEsperado));

        Pagamento resultado = pagamentoService.buscarPedidoPeloId(pagamentoEsperado.getIdPagamento());

        assertNotNull(resultado);
        assertEquals(pagamentoEsperado.getIdPagamento(), resultado.getIdPagamento());
    }

    @Test
    void atualizarStatus_QuandoPagamentoPendente_DeveAtualizarParaProcessando() {
        when(pagamentoRepository.findById(pagamentoEsperado.getIdPagamento())).thenReturn(Optional.of(pagamentoEsperado));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoEsperado);

        Pagamento resultado = pagamentoService.atualizarSatus(pagamentoEsperado.getIdPagamento());

        assertNotNull(resultado);
        assertEquals(StatusPagamento.PROCESSANDO, resultado.getStatusPagamento());
        assertNotNull(resultado.getAtualizacaoStatus());
        verify(pagamentoRepository).findById(pagamentoEsperado.getIdPagamento());
        verify(pagamentoRepository).save(any(Pagamento.class));

    }

    @Test
    void atualizarStatus_QuandoPagamentoProcessando_DeveAtualizarParaAprovado() {
        Pagamento pagamentoProcessando = pagamentoEsperado;
        pagamentoProcessando.setStatusPagamento(StatusPagamento.PROCESSANDO);

        when(pagamentoRepository.findById(pagamentoEsperado.getIdPagamento())).thenReturn(Optional.of(pagamentoProcessando));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoProcessando);

        Pagamento resultado = pagamentoService.atualizarSatus(pagamentoProcessando.getIdPagamento());

        assertNotNull(resultado);
        assertEquals(StatusPagamento.APROVADO, resultado.getStatusPagamento());
        assertNotNull(resultado.getAtualizacaoStatus());
        verify(pagamentoRepository).findById(pagamentoProcessando.getIdPagamento());
        verify(pagamentoRepository).save(any(Pagamento.class));

    }

    @Test
    void aprovarPagamento_QuandoDadosValidos_DeveRetornarPagamentoAprovado() {
        when(pagamentoRepository.findById(pagamentoEsperado.getIdPagamento())).thenReturn(Optional.of(pagamentoEsperado));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoEsperado);

        Pagamento resultado = pagamentoService.aprovarPagamento(pagamentoEsperado.getIdPagamento());

        assertNotNull(resultado);
        assertEquals(StatusPagamento.APROVADO, resultado.getStatusPagamento());
        assertNotNull(resultado.getAtualizacaoStatus());
        verify(pagamentoRepository).findById(pagamentoEsperado.getIdPagamento());
        verify(pagamentoRepository).save(any(Pagamento.class));
    }

    @Test
    void rejeitarPagamento_QuandoDadosValidos_DeveRetornarPagamentoRejeitado() {
        when(pagamentoRepository.findById(pagamentoEsperado.getIdPagamento())).thenReturn(Optional.of(pagamentoEsperado));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoEsperado);

        Pagamento resultado = pagamentoService.rejeitarPagamento(pagamentoEsperado.getIdPagamento());

        assertNotNull(resultado);
        assertEquals(StatusPagamento.REJEITADO, resultado.getStatusPagamento());
        assertNotNull(resultado.getAtualizacaoStatus());
        verify(pagamentoRepository).findById(pagamentoEsperado.getIdPagamento());
        verify(pagamentoRepository).save(any(Pagamento.class));
    }

    @Test
    void cancelarPagamento_QuandoDadosValidos_DeveRetornarPagamentoCancelado() {
        when(pagamentoRepository.findById(pagamentoEsperado.getIdPagamento())).thenReturn(Optional.of(pagamentoEsperado));
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(pagamentoEsperado);

        Pagamento resultado = pagamentoService.cancelarPagamento(pagamentoEsperado.getIdPagamento());

        assertNotNull(resultado);
        assertEquals(StatusPagamento.CANCELADO, resultado.getStatusPagamento());
        assertNotNull(resultado.getAtualizacaoStatus());
        verify(pagamentoRepository).findById(pagamentoEsperado.getIdPagamento());
        verify(pagamentoRepository).save(any(Pagamento.class));
    }

    @Test
    void deveRetornarPaginaComPagamentosDoCliente() {
        String idCliente = "cliente123";
        int page = 0;
        int size = 20;

        List<Pagamento> pagamentos = List.of(
                new Pagamento(),
                new Pagamento()
        );

        Page<Pagamento> paginaPagamentos = new PageImpl<>(pagamentos);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        when(pagamentoRepository.findByIdCliente(eq(idCliente), any(PageRequest.class)))
                .thenReturn(paginaPagamentos);

        Page<Pagamento> resultado = pagamentoService.buscarPagamentosDoCliente(idCliente, page, size);


        assertNotNull(resultado);
        assertEquals(2, resultado.getContent().size());
        assertEquals(paginaPagamentos, resultado);
    }

    @Test
    void deveRetornarPaginaComPagamentosDoClienteOrdenadoPorDataPagamento() {
        Page<Pagamento> paginaVazia = new PageImpl<>(List.of(pagamentoEsperado));

        when(pagamentoRepository.findAll(any(PageRequest.class)))
                                .thenReturn(paginaVazia);

        int page = 0;
        int size = 20;
        Page<Pagamento> resultado = pagamentoService.listarPagamentos(page, size);

        assertFalse(resultado.getContent().isEmpty());
        assertEquals(1, resultado.getTotalElements());
        assertEquals(pagamentoEsperado.getIdPagamento(), resultado.getContent().get(0).getIdPagamento());
    }

    @Test
    void deveRetornarPaginaVaziaQuandoNaoExistemPagamentosParaCliente() {
        String idCliente = "1234567890";
        int page = 0;
        int size = 20;

        Page<Pagamento> paginaVazia = new PageImpl<>(List.of());

        when(pagamentoRepository.findByIdCliente(eq(idCliente), any(PageRequest.class)))
                .thenReturn(paginaVazia);

        Page<Pagamento> resultado = pagamentoService.buscarPagamentosDoCliente(idCliente, page, size);


        assertNotNull(resultado);
        assertTrue(resultado.getContent().isEmpty());
        assertEquals(0, resultado.getTotalElements());
    }

    @Test
    void deveManterOrdenacaoAscendentePorId() {
        String idCliente = "cliente123";
        int page = 0;
        int size = 20;

        pagamentoService.buscarPagamentosDoCliente(idCliente, page, size);

        PageRequest expectedPageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        verify(pagamentoRepository).findByIdCliente(eq(idCliente), eq(expectedPageRequest));
    }

}