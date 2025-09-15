package br.com.gestao_colaboradores_api.exceptions;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }

    public DuplicateResourceException(String resourceName, String identifier) {
        super(resourceName + " já existe com identificador: " + identifier);
    }
}