package br.com.fiaap.mspagamento.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CartaoInvalidoException extends RuntimeException {
    public CartaoInvalidoException(String mensagem) {
        super(mensagem);
    }
}
