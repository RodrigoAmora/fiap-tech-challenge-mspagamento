package br.com.fiaap.mspagamento.validator;

import br.com.fiaap.mspagamento.domain.Cartao;
import br.com.fiaap.mspagamento.exception.CartaoInvalidoException;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class CartaoValidator {

    private static final Pattern NUMERO_CARTAO_PATTERN = Pattern.compile("^[0-9]{16}$");
    private static final Pattern CVV_PATTERN = Pattern.compile("^[0-9]{3,4}$");
    private static final Pattern DATA_VALIDADE_PATTERN = Pattern.compile("^(0[1-9]|1[0-2])/([0-9]{2})$");

    public static void validarCartao(Cartao cartao) {
        if (cartao == null) {
            throw new CartaoInvalidoException("Dados do cartão não podem ser nulos");
        }

        validarNumeroCartao(cartao.getNumeroCartao());
        validarNomeTitular(cartao.getNomeTitular());
        validarDataValidade(cartao.getDataValidade());
        validarCodigoSeguranca(cartao.getCodigoSeguranca());
    }

    private static void validarNumeroCartao(String numeroCartao) {
        if (numeroCartao == null || numeroCartao.trim().isEmpty()) {
            throw new CartaoInvalidoException("Número do cartão é obrigatório");
        }

        if (!NUMERO_CARTAO_PATTERN.matcher(numeroCartao.replaceAll(" ", "")).matches()) {
            throw new CartaoInvalidoException("Número do cartão inválido: deve conter entre 16 dígitos");
        }

        if (!validarAlgoritmoLuhn(numeroCartao)) {
            throw new CartaoInvalidoException("Número do cartão inválido: não passou na validação do algoritmo de Luhn");
        }
    }

    private static boolean validarAlgoritmoLuhn(String numeroCartao) {
        int soma = 0;
        boolean alternar = false;

        for (int i = numeroCartao.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(numeroCartao.substring(i, i + 1));
            if (alternar) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            soma += n;
            alternar = !alternar;
        }

        return (soma % 10 == 0);
    }

    private static void validarNomeTitular(String nomeTitular) {
        if (nomeTitular == null || nomeTitular.trim().isEmpty()) {
            throw new CartaoInvalidoException("Nome do titular é obrigatório");
        }

        if (nomeTitular.trim().length() < 3) {
            throw new CartaoInvalidoException("Nome do titular deve ter pelo menos 3 caracteres");
        }

        if (!nomeTitular.matches("^[a-zA-ZÀ-ÿ\\s']+$")) {
            throw new CartaoInvalidoException("Nome do titular contém caracteres inválidos");
        }
    }

    private static void validarDataValidade(String dataValidade) {
        if (dataValidade == null || dataValidade.trim().isEmpty()) {
            throw new CartaoInvalidoException("Data de validade é obrigatória");
        }

        if (!DATA_VALIDADE_PATTERN.matcher(dataValidade).matches()) {
            throw new CartaoInvalidoException("Data de validade inválida: use o formato MM/YY");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            YearMonth dataCartao = YearMonth.parse(dataValidade, formatter);
            YearMonth hoje = YearMonth.now();

            if (dataCartao.isBefore(hoje)) {
                throw new CartaoInvalidoException("Cartão expirado");
            }
        } catch (DateTimeParseException e) {
            throw new CartaoInvalidoException("Data de validade inválida");
        }
    }

    private static void validarCodigoSeguranca(String cvv) {
        if (cvv == null || cvv.trim().isEmpty()) {
            throw new CartaoInvalidoException("Código de segurança é obrigatório");
        }

        if (!CVV_PATTERN.matcher(cvv).matches()) {
            throw new CartaoInvalidoException("Código de segurança inválido: deve conter 3 ou 4 dígitos");
        }
    }

}
