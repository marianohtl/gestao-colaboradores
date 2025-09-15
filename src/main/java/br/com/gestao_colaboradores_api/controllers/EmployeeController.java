package br.com.gestao_colaboradores_api.controllers;


import br.com.gestao_colaboradores_api.dtos.CreateEmployeeRequest;
import br.com.gestao_colaboradores_api.dtos.EmployeeResponse;
import br.com.gestao_colaboradores_api.services.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/employees")
@Tag(name = "Employees", description = "Gestão de colaboradores")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os colaboradores")
    public List<EmployeeResponse> getAll() {
        return employeeService.getAllEmployees();
    }

    @PostMapping
    @Operation(summary = "Criar novo colaborador")
    public ResponseEntity<EmployeeResponse> create(@Valid @RequestBody CreateEmployeeRequest request) {
        EmployeeResponse response = employeeService.createEmployee(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir colaborador")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Buscar colaboradores por departamento")
    public List<EmployeeResponse> searchByDepartment(@RequestParam String department) {
        return employeeService.searchByDepartment(department);
    }
}