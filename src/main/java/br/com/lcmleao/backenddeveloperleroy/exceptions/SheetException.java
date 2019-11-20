package br.com.lcmleao.backenddeveloperleroy.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Representa uma exceção no processamento da planilha
 * */
public class SheetException extends RuntimeException {
    @Getter
    private int httpStatus = 500;

    public SheetException() {
    }
    public SheetException(String message) {
        this(message, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    public SheetException(String message, int httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public SheetException(String message, Throwable cause) {
        super(message, cause);
    }

    public SheetException(Throwable cause) {
        super(cause);
    }
}
