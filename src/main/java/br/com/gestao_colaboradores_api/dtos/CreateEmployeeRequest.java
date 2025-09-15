package br.com.gestao_colaboradores_api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateEmployeeRequest(
        @NotBlank(message = "Nome do colaborador é obrigatório") String name,
        @NotBlank(message = "E-mail do colaborador é obrigatório") @Email(message = "Deve conter um endereço de e-mail válido.") String email,
        @NotNull(message = "Id do departamento é obrigatório") UUID departmentId,
        @NotNull(message = "Id do cargo é obrigatório") UUID positionId
) {}