package br.com.gestao_colaboradores_api.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resourceName, UUID id) {
        super(resourceName + " não encontrado com ID: " + id);
    }

    public ResourceNotFoundException(String resourceName, String identifier) {
        super(resourceName + " não encontrado com identificador: " + identifier);
    }
}