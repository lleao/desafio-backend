package br.com.lcmleao.backenddeveloperleroy.exceptions;

/**
 * Representa uma exceção no processamento da planilha
 * */
public class SheetProcessException extends RuntimeException {
    public SheetProcessException() {
    }

    public SheetProcessException(String message) {
        super(message);
    }

    public SheetProcessException(String message, Throwable cause) {
        super(message, cause);
    }

    public SheetProcessException(Throwable cause) {
        super(cause);
    }
}
