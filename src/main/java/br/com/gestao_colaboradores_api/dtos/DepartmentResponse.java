package br.com.gestao_colaboradores_api.dtos;

import java.util.UUID;

public record DepartmentResponse(
        UUID id,
        String name
) {}