package br.com.gestao_colaboradores_api.services;

import br.com.gestao_colaboradores_api.dtos.DepartmentRequest;
import br.com.gestao_colaboradores_api.dtos.DepartmentResponse;
import br.com.gestao_colaboradores_api.exceptions.DuplicateResourceException;
import br.com.gestao_colaboradores_api.models.Department;
import br.com.gestao_colaboradores_api.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @InjectMocks
    private DepartmentService departmentService;

    @Test
    void createDepartment_WithValidData_ShouldReturnDepartmentResponse() {
        // Arrange
        DepartmentRequest request = new DepartmentRequest("TI");
        Department department = new Department();
        department.setId(UUID.randomUUID());
        department.setName("TI");

        when(departmentRepository.existsByName("TI")).thenReturn(false);
        when(departmentRepository.save(any(Department.class))).thenReturn(department);

        // Act
        DepartmentResponse response = departmentService.createDepartment(request);

        // Assert
        assertNotNull(response);
        assertEquals("TI", response.name());
        assertNotNull(response.id());

        verify(departmentRepository, times(1)).existsByName("TI");
        verify(departmentRepository, times(1)).save(any(Department.class));
    }

    @Test
    void createDepartment_WithDuplicateName_ShouldThrowDuplicateResourceException() {
        // Arrange
        DepartmentRequest request = new DepartmentRequest("TI");

        when(departmentRepository.existsByName("TI")).thenReturn(true);

        // Act & Assert
        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> departmentService.createDepartment(request)
        );

        assertEquals("Departamento já existe com identificador: TI", exception.getMessage());
        verify(departmentRepository, times(1)).existsByName("TI");
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void createDepartment_WithNullName_ShouldThrowIllegalArgumentException() {
        // Arrange
        DepartmentRequest request = new DepartmentRequest(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> departmentService.createDepartment(request)
        );

        assertEquals("Nome do departamento é obrigatório", exception.getMessage());
        verify(departmentRepository, never()).existsByName(anyString());
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void createDepartment_WithEmptyName_ShouldThrowIllegalArgumentException() {
        // Arrange
        DepartmentRequest request = new DepartmentRequest("   ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> departmentService.createDepartment(request)
        );

        assertEquals("Nome do departamento é obrigatório", exception.getMessage());
        verify(departmentRepository, never()).existsByName(anyString());
        verify(departmentRepository, never()).save(any(Department.class));
    }

    @Test
    void getAllDepartments_ShouldReturnListOfDepartments() {
        // Arrange
        Department department1 = new Department(UUID.randomUUID(), "TI");
        Department department2 = new Department(UUID.randomUUID(), "RH");
        List<Department> departments = List.of(department1, department2);

        when(departmentRepository.findAll()).thenReturn(departments);

        // Act
        List<DepartmentResponse> responses = departmentService.getAllDepartments();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("TI", responses.get(0).name());
        assertEquals("RH", responses.get(1).name());

        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getAllDepartments_WhenNoDepartments_ShouldReturnEmptyList() {
        // Arrange
        when(departmentRepository.findAll()).thenReturn(List.of());

        // Act
        List<DepartmentResponse> responses = departmentService.getAllDepartments();

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(departmentRepository, times(1)).findAll();
    }

    @Test
    void getDepartmentName_WithValidId_ShouldReturnDepartmentName() {
        // Arrange
        UUID departmentId = UUID.randomUUID();
        Department department = new Department(departmentId, "TI");

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.of(department));

        // Act
        String departmentName = departmentService.getDepartmentName(departmentId);

        // Assert
        assertEquals("TI", departmentName);
        verify(departmentRepository, times(1)).findById(departmentId);
    }

    @Test
    void getDepartmentName_WithNonExistentId_ShouldReturnNull() {
        // Arrange
        UUID departmentId = UUID.randomUUID();

        when(departmentRepository.findById(departmentId)).thenReturn(Optional.empty());

        // Act
        String departmentName = departmentService.getDepartmentName(departmentId);

        // Assert
        assertNull(departmentName);
        verify(departmentRepository, times(1)).findById(departmentId);
    }

    @Test
    void getDepartmentName_WithNullId_ShouldReturnNull() {
        // Act
        String departmentName = departmentService.getDepartmentName(null);

        // Assert
        assertNull(departmentName);
        verify(departmentRepository, never()).findById(any(UUID.class));
    }

    @Test
    void toResponse_ShouldConvertDepartmentToResponseCorrectly() {
        // Arrange
        UUID id = UUID.randomUUID();
        Department department = new Department(id, "TI");

        // Act
        DepartmentResponse response = departmentService.toResponse(department);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("TI", response.name());
    }

    @Test
    void validateDepartmentName_WithValidName_ShouldNotThrowException() {
        // Arrange
        when(departmentRepository.existsByName("TI")).thenReturn(false);
        when(departmentRepository.save(any())).thenReturn(new Department());

        // Act & Assert
        assertDoesNotThrow(() -> {
            departmentService.createDepartment(new DepartmentRequest("TI"));
        });
    }

    @Test
    void validateDepartmentName_WithDuplicateName_ShouldThrowException() {
        // Arrange
        when(departmentRepository.existsByName("TI")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            departmentService.createDepartment(new DepartmentRequest("TI"));
        });
    }
}