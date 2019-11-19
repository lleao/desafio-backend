package br.com.lcmleao.backenddeveloperleroy.exceptions;

/**
 * Representa uma exceção na execução de armazenamento de um arquivo
 * */
public class StorageException extends RuntimeException {
    public StorageException() {
    }

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageException(Throwable cause) {
        super(cause);
    }
}
