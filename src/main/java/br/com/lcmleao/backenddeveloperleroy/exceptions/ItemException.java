package br.com.lcmleao.backenddeveloperleroy.exceptions;

/**
 * Representa uma exceção na execução de armazenamento de um arquivo
 * */
public class ItemException extends RuntimeException {
    public ItemException() {
    }

    public ItemException(String message) {
        super(message);
    }

    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemException(Throwable cause) {
        super(cause);
    }
}
