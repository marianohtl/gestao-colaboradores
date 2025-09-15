package br.com.gestao_colaboradores_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateEmployeeRequest(
        @NotBlank String name,
        @NotBlank @Email String email,
        @NotNull UUID departmentId,
        @NotNull UUID positionId
) {}