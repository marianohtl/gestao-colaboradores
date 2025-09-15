package br.com.gestao_colaboradores_api.services;

import br.com.gestao_colaboradores_api.dtos.CreateEmployeeRequest;
import br.com.gestao_colaboradores_api.dtos.EmployeeResponse;
import br.com.gestao_colaboradores_api.dtos.enums.EmployeeStatus;
import br.com.gestao_colaboradores_api.exceptions.ResourceNotFoundException;
import br.com.gestao_colaboradores_api.models.Employee;
import br.com.gestao_colaboradores_api.repositories.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DepartmentService departmentService;

    @Mock
    private PositionService positionService;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    void createEmployee_WithValidData_ShouldReturnEmployeeResponse() {
        // Arrange
        UUID departmentId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "João Silva", "joao@email.com", departmentId, positionId
        );

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("João Silva");
        employee.setEmail("joao@email.com");
        employee.setDepartmentId(departmentId);
        employee.setPositionId(positionId);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setStatus(EmployeeStatus.ATIVO);

        when(departmentService.getDepartmentName(departmentId)).thenReturn("TI");
        when(positionService.getPositionTitle(positionId)).thenReturn("Desenvolvedor");
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act
        EmployeeResponse response = employeeService.createEmployee(request);

        // Assert
        assertNotNull(response);
        assertEquals("João Silva", response.name());
        assertEquals("joao@email.com", response.email());
        assertEquals("TI", response.department());
        assertEquals("Desenvolvedor", response.position());
        assertEquals("ATIVO", response.status());
        assertNotNull(response.createdAt());

        verify(departmentService, times(2)).getDepartmentName(departmentId);
        verify(positionService, times(2)).getPositionTitle(positionId);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void createEmployee_WithNonExistentDepartment_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID departmentId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "João Silva", "joao@email.com", departmentId, positionId
        );

        when(departmentService.getDepartmentName(departmentId)).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.createEmployee(request)
        );

        assertTrue(exception.getMessage().contains("Departamento"));
        verify(departmentService, times(1)).getDepartmentName(departmentId);
        verify(positionService, never()).getPositionTitle(any());
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void createEmployee_WithNonExistentPosition_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID departmentId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();
        CreateEmployeeRequest request = new CreateEmployeeRequest(
                "João Silva", "joao@email.com", departmentId, positionId
        );

        when(departmentService.getDepartmentName(departmentId)).thenReturn("TI");
        when(positionService.getPositionTitle(positionId)).thenReturn(null);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.createEmployee(request)
        );

        assertTrue(exception.getMessage().contains("Cargo"));
        verify(departmentService, times(1)).getDepartmentName(departmentId);
        verify(positionService, times(1)).getPositionTitle(positionId);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void getAllEmployees_ShouldReturnListOfEmployees() {
        // Arrange
        UUID departmentId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();

        Employee employee1 = new Employee();
        employee1.setId(UUID.randomUUID());
        employee1.setName("João Silva");
        employee1.setEmail("joao@email.com");
        employee1.setDepartmentId(departmentId);
        employee1.setPositionId(positionId);
        employee1.setCreatedAt(LocalDateTime.now());
        employee1.setStatus(EmployeeStatus.ATIVO);

        Employee employee2 = new Employee();
        employee2.setId(UUID.randomUUID());
        employee2.setName("Maria Santos");
        employee2.setEmail("maria@email.com");
        employee2.setDepartmentId(departmentId);
        employee2.setPositionId(positionId);
        employee2.setCreatedAt(LocalDateTime.now());
        employee2.setStatus(EmployeeStatus.ATIVO);

        when(employeeRepository.findAll()).thenReturn(List.of(employee1, employee2));
        when(departmentService.getDepartmentName(departmentId)).thenReturn("TI");
        when(positionService.getPositionTitle(positionId)).thenReturn("Desenvolvedor");

        // Act
        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());

        assertEquals("João Silva", responses.get(0).name());
        assertEquals("Maria Santos", responses.get(1).name());

        verify(employeeRepository, times(1)).findAll();
        verify(departmentService, times(2)).getDepartmentName(departmentId);
        verify(positionService, times(2)).getPositionTitle(positionId);
    }

    @Test
    void getAllEmployees_WhenNoEmployees_ShouldReturnEmptyList() {
        // Arrange
        when(employeeRepository.findAll()).thenReturn(List.of());

        // Act
        List<EmployeeResponse> responses = employeeService.getAllEmployees();

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(employeeRepository, times(1)).findAll();
        verify(departmentService, never()).getDepartmentName(any());
        verify(positionService, never()).getPositionTitle(any());
    }

    @Test
    void searchByDepartment_WithValidDepartment_ShouldReturnFilteredEmployees() {
        // Arrange
        UUID departmentId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();

        Employee employee = new Employee();
        employee.setId(UUID.randomUUID());
        employee.setName("João Silva");
        employee.setEmail("joao@email.com");
        employee.setDepartmentId(departmentId);
        employee.setPositionId(positionId);
        employee.setCreatedAt(LocalDateTime.now());
        employee.setStatus(EmployeeStatus.ATIVO);

        when(employeeRepository.findByDepartmentName("TI")).thenReturn(List.of(employee));
        when(departmentService.getDepartmentName(departmentId)).thenReturn("TI");
        when(positionService.getPositionTitle(positionId)).thenReturn("Desenvolvedor");

        // Act
        List<EmployeeResponse> responses = employeeService.searchByDepartment("TI");

        // Assert
        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals("João Silva", responses.get(0).name());
        assertEquals("TI", responses.get(0).department());

        verify(employeeRepository, times(1)).findByDepartmentName("TI");
        verify(departmentService, times(1)).getDepartmentName(departmentId);
        verify(positionService, times(1)).getPositionTitle(positionId);
    }

    @Test
    void searchByDepartment_WithNoMatchingEmployees_ShouldReturnEmptyList() {
        // Arrange
        when(employeeRepository.findByDepartmentName("RH")).thenReturn(List.of());

        // Act
        List<EmployeeResponse> responses = employeeService.searchByDepartment("RH");

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(employeeRepository, times(1)).findByDepartmentName("RH");
        verify(departmentService, never()).getDepartmentName(any());
        verify(positionService, never()).getPositionTitle(any());
    }

    @Test
    void deleteEmployee_WithValidId_ShouldDeleteEmployee() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.existsById(employeeId)).thenReturn(true);

        // Act
        employeeService.deleteEmployee(employeeId);

        // Assert
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, times(1)).deleteById(employeeId);
    }

    @Test
    void deleteEmployee_WithNonExistentId_ShouldThrowResourceNotFoundException() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        when(employeeRepository.existsById(employeeId)).thenReturn(false);

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> employeeService.deleteEmployee(employeeId)
        );

        assertTrue(exception.getMessage().contains("Colaborador"));
        verify(employeeRepository, times(1)).existsById(employeeId);
        verify(employeeRepository, never()).deleteById(any());
    }

    @Test
    void toResponse_ShouldConvertEmployeeToResponseCorrectly() {
        // Arrange
        UUID employeeId = UUID.randomUUID();
        UUID departmentId = UUID.randomUUID();
        UUID positionId = UUID.randomUUID();
        LocalDateTime createdAt = LocalDateTime.now();

        Employee employee = new Employee();
        employee.setId(employeeId);
        employee.setName("João Silva");
        employee.setEmail("joao@email.com");
        employee.setDepartmentId(departmentId);
        employee.setPositionId(positionId);
        employee.setCreatedAt(createdAt);
        employee.setStatus(EmployeeStatus.ATIVO);

        when(departmentService.getDepartmentName(departmentId)).thenReturn("TI");
        when(positionService.getPositionTitle(positionId)).thenReturn("Desenvolvedor");

        // Act
        EmployeeResponse response = employeeService.toResponse(employee);

        // Assert
        assertNotNull(response);
        assertEquals(employeeId, response.id());
        assertEquals("João Silva", response.name());
        assertEquals("joao@email.com", response.email());
        assertEquals("TI", response.department());
        assertEquals("Desenvolvedor", response.position());
        assertEquals("ATIVO", response.status());
        assertEquals(createdAt, response.createdAt());

        verify(departmentService, times(1)).getDepartmentName(departmentId);
        verify(positionService, times(1)).getPositionTitle(positionId);
    }
}