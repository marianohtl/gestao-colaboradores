package br.com.gestao_colaboradores_api.dtos;

import java.util.UUID;

public record PositionResponse(
        UUID id,
        String title
) {}