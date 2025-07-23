package br.com.fiaap.mspagamento.validator;

import br.com.fiaap.mspagamento.domain.Cartao;
import br.com.fiaap.mspagamento.exception.CartaoInvalidoException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class CartaoValidatorTest {

    @Test
    @DisplayName("Deve validar cartão com dados corretos")
    void deveValidarCartaoComDadosCorretos() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade(obterDataValidadeFutura());
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertDoesNotThrow(() -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    @DisplayName("Deve lançar exceção quando cartão for nulo")
    void deveLancarExcecaoQuandoCartaoForNulo() {
        // Act & Assert
        CartaoInvalidoException exception = assertThrows(
                CartaoInvalidoException.class,
                () -> CartaoValidator.validarCartao(null)
        );
        assertEquals("Dados do cartão não podem ser nulos", exception.getMessage());
    }

    @ParameterizedTest
    @DisplayName("Deve lançar exceção para números de cartão inválidos")
    @ValueSource(strings = {
            "1234567890123456", // Número inválido pelo algoritmo de Luhn
            "123456",           // Número muito curto
            "12345678901234567890", // Número muito longo
            "abcd5678901234567890" , // Contém letras
            " " // Vazio
    })
    void deveLancarExcecaoParaNumerosDeCartaoInvalidos(String numeroCartao) {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade(obterDataValidadeFutura());
        cartao.setNumeroCartao(numeroCartao);
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    void deveLancarExcecaoParaNumeroNulo() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade("01/01");
        cartao.setNumeroCartao(null);
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar exceção para nomes de titular inválidos")
    @NullAndEmptySource
    @ValueSource(strings = {
            "   ",          // Apenas espaços
            "Ab",           // Nome muito curto
            "João123",      // Contém números
            "João@Silva",   // Contém caracteres especiais
    })
    void deveLancarExcecaoParaNomesDeTitularInvalidos(String nomeTitular) {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade(obterDataValidadeFutura());
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular(nomeTitular);

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar exceção para datas de validade inválidas")
    @ValueSource(strings = {
            "13/25",    // Mês inválido
            "00/25",    // Mês inválido
            "12/19",    // Data passada
            "12/2025",  // Formato incorreto
            "12-25",    // Formato incorreto
            "1225",     // Formato incorreto
            " " // Vazio
    })
    void deveLancarExcecaoParaDatasDeValidadeInvalidas(String dataValidade) {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade(dataValidade);
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    void deveLancarExcecaoParaCartaoExpirado() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade("01/01");
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    void deveLancarExcecaoParaDataNoFormatoInvalido() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade("20/01/01");
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    void deveLancarExcecaoParaDataDeValidadeNula() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade(null);
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    void deveLancarExcecaoParaDataDeValidadeInvalida() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade("aa/01");
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @ParameterizedTest
    @DisplayName("Deve lançar exceção para códigos de segurança inválidos")
    @NullAndEmptySource
    @ValueSource(strings = {
            "12",       // Muito curto
            "12345",    // Muito longo
            "abc",      // Contém letras
            "1a2",      // Contém letras
            "    "      // Apenas espaços
    })
    void deveLancarExcecaoParaCodigosDeSegurancaInvalidos(String cvv) {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca(cvv);
        cartao.setDataValidade(obterDataValidadeFutura());
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertThrows(CartaoInvalidoException.class, () -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    @DisplayName("Deve aceitar CVV com 3 dígitos")
    void deveAceitarCVVComTresDigitos() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("123");
        cartao.setDataValidade(obterDataValidadeFutura());
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertDoesNotThrow(() -> CartaoValidator.validarCartao(cartao));
    }

    @Test
    @DisplayName("Deve aceitar CVV com 4 dígitos")
    void deveAceitarCVVComQuatroDigitos() {
        // Arrange
        Cartao cartao = new Cartao();
        cartao.setBandeira("VISA");
        cartao.setCodigoSeguranca("1234");
        cartao.setDataValidade(obterDataValidadeFutura());
        cartao.setNumeroCartao("4532756279624064");
        cartao.setNomeTitular("João da Silva");

        // Act & Assert
        assertDoesNotThrow(() -> CartaoValidator.validarCartao(cartao));
    }

    // Método auxiliar para gerar uma data de validade futura
    private String obterDataValidadeFutura() {
        YearMonth dataFutura = YearMonth.now().plusMonths(1);
        return dataFutura.format(DateTimeFormatter.ofPattern("MM/yy"));
    }
}
