package br.com.lcmleao.backenddeveloperleroy.exceptions;

/**
 * Representa uma exceção na inicialização do diretório de armazenamento
 * */
public class StorageInitException extends RuntimeException {
    public StorageInitException() {
    }

    public StorageInitException(String message) {
        super(message);
    }

    public StorageInitException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageInitException(Throwable cause) {
        super(cause);
    }
}
