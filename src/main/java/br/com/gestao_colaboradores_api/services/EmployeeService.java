package br.com.gestao_colaboradores_api.services;

import br.com.gestao_colaboradores_api.dtos.CreateEmployeeRequest;
import br.com.gestao_colaboradores_api.dtos.EmployeeResponse;
import br.com.gestao_colaboradores_api.dtos.enums.EmployeeStatus;
import br.com.gestao_colaboradores_api.exceptions.ResourceNotFoundException;
import br.com.gestao_colaboradores_api.models.Employee;
import br.com.gestao_colaboradores_api.repositories.EmployeeRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentService departmentService;
    private final PositionService positionService;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentService departmentService,
                           PositionService positionService) {
        this.employeeRepository = employeeRepository;
        this.departmentService = departmentService;
        this.positionService = positionService;
    }

    public EmployeeResponse createEmployee(CreateEmployeeRequest request) {
        validateDepartment(request.departmentId());
        validatePosition(request.positionId());

        Employee employee = new Employee();
        employee.setName(request.name());
        employee.setEmail(request.email());
        employee.setDepartmentId(request.departmentId());
        employee.setPositionId(request.positionId());
        employee.setCreatedAt(LocalDateTime.now());
        employee.setStatus(EmployeeStatus.ATIVO);

        Employee saved = employeeRepository.save(employee);
        return toResponse(saved);
    }

    public List<EmployeeResponse> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public List<EmployeeResponse> searchByDepartment(String departmentName) {
        return employeeRepository.findByDepartmentName(departmentName).stream()
                .map(this::toResponse)
                .toList();
    }

    public void deleteEmployee(UUID id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colaborador", id);
        }
        employeeRepository.deleteById(id);
    }

    protected EmployeeResponse toResponse(Employee employee) {
        String departmentName = departmentService.getDepartmentName(employee.getDepartmentId());
        String positionTitle = positionService.getPositionTitle(employee.getPositionId());

        return new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                departmentName,
                positionTitle,
                employee.getStatus().name(),
                employee.getCreatedAt()
        );
    }
    private void validatePosition(@NotNull(message = "Id do cargo é obrigatório") UUID uuid) {
        String position = positionService.getPositionTitle(uuid);
        if(position == null)
            throw new ResourceNotFoundException("Cargo", uuid);
    }

    private void validateDepartment(@NotNull(message = "Id do departamento é obrigatório") UUID uuid) {
        String department = departmentService.getDepartmentName(uuid);
        if(department == null)
            throw new ResourceNotFoundException("Departamento", uuid);

    }
}
