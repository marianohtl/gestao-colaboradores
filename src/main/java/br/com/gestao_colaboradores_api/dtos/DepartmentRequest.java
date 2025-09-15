package br.com.gestao_colaboradores_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(
        @NotBlank(message = "Nome do departamento é obrigatório")
        String name
) {}