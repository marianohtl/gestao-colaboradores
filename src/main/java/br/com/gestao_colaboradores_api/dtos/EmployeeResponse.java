package br.com.gestao_colaboradores_api.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeResponse(
        UUID id,
        String name,
        String email,
        String department,
        String position,
        String status,
        LocalDateTime createdAt
) {}