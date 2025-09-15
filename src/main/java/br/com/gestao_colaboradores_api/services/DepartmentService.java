package br.com.gestao_colaboradores_api.services;

import br.com.gestao_colaboradores_api.dtos.DepartmentRequest;
import br.com.gestao_colaboradores_api.dtos.DepartmentResponse;
import br.com.gestao_colaboradores_api.exceptions.DuplicateResourceException;
import br.com.gestao_colaboradores_api.models.Department;
import br.com.gestao_colaboradores_api.repositories.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public DepartmentResponse createDepartment(DepartmentRequest request) {
        validateDepartmentName(request.name());

        Department department = new Department();
        department.setName(request.name());

        Department savedDepartment = departmentRepository.save(department);
        return toResponse(savedDepartment);
    }

    public List<DepartmentResponse> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public String getDepartmentName(UUID departmentId) {
        if (departmentId == null) {
            return "Departamento não informado";
        }

        return departmentRepository.findById(departmentId)
                .map(Department::getName)
                .orElse("Departamento não encontrado");
    }

    private void validateDepartmentName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do departamento é obrigatório");
        }

        if (departmentRepository.existsByName(name)) {
            throw new DuplicateResourceException("Departamento", name);
        }
    }

    private DepartmentResponse toResponse(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName()
        );
    }
}
