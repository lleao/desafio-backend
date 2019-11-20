package br.com.lcmleao.backenddeveloperleroy.exceptions;

/**
 * Representa uma exceção na execução de armazenamento de um arquivo
 * */
public class CategoryException extends RuntimeException {
    public CategoryException() {
    }

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public CategoryException(Throwable cause) {
        super(cause);
    }
}
