package br.com.gestao_colaboradores_api.dtos;

import jakarta.validation.constraints.NotBlank;

public record PositionRequest(
        @NotBlank(message = "Título do cargo é obrigatório")
        String title
) {}