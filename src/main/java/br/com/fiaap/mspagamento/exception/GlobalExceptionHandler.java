package br.com.fiaap.mspagamento.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CartaoInvalidoException.class)
    public ResponseEntity<ErroResponse> handleCartaoInvalidoException(CartaoInvalidoException ex) {
        ErroResponse erro = new ErroResponse(
                "CARTAO_INVALIDO",
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(erro);
    }
}

record ErroResponse(String codigo, String mensagem) {}
