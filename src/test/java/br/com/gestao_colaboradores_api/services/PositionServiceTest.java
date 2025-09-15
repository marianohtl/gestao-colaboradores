package br.com.gestao_colaboradores_api.services;

import br.com.gestao_colaboradores_api.dtos.PositionRequest;
import br.com.gestao_colaboradores_api.dtos.PositionResponse;
import br.com.gestao_colaboradores_api.exceptions.DuplicateResourceException;
import br.com.gestao_colaboradores_api.models.Position;
import br.com.gestao_colaboradores_api.repositories.PositionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest {

    @Mock
    private PositionRepository positionRepository;

    @InjectMocks
    private PositionService positionService;

    @Test
    void createPosition_WithValidData_ShouldReturnPositionResponse() {
        // Arrange
        PositionRequest request = new PositionRequest("Desenvolvedor");
        Position position = new Position();
        position.setId(UUID.randomUUID());
        position.setTitle("Desenvolvedor");

        when(positionRepository.existsByTitle("Desenvolvedor")).thenReturn(false);
        when(positionRepository.save(any(Position.class))).thenReturn(position);

        // Act
        PositionResponse response = positionService.createPosition(request);

        // Assert
        assertNotNull(response);
        assertEquals("Desenvolvedor", response.title());
        assertNotNull(response.id());

        verify(positionRepository, times(1)).existsByTitle("Desenvolvedor");
        verify(positionRepository, times(1)).save(any(Position.class));
    }

    @Test
    void createPosition_WithDuplicateTitle_ShouldThrowDuplicateResourceException() {
        // Arrange
        PositionRequest request = new PositionRequest("Desenvolvedor");

        when(positionRepository.existsByTitle("Desenvolvedor")).thenReturn(true);

        // Act & Assert
        DuplicateResourceException exception = assertThrows(
                DuplicateResourceException.class,
                () -> positionService.createPosition(request)
        );

        assertEquals("Cargo já existe com identificador: Desenvolvedor", exception.getMessage());
        verify(positionRepository, times(1)).existsByTitle("Desenvolvedor");
        verify(positionRepository, never()).save(any(Position.class));
    }

    @Test
    void createPosition_WithNullTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        PositionRequest request = new PositionRequest(null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> positionService.createPosition(request)
        );

        assertEquals("Título do cargo é obrigatório", exception.getMessage());
        verify(positionRepository, never()).existsByTitle(anyString());
        verify(positionRepository, never()).save(any(Position.class));
    }

    @Test
    void createPosition_WithEmptyTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        PositionRequest request = new PositionRequest("   ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> positionService.createPosition(request)
        );

        assertEquals("Título do cargo é obrigatório", exception.getMessage());
        verify(positionRepository, never()).existsByTitle(anyString());
        verify(positionRepository, never()).save(any(Position.class));
    }

    @Test
    void createPosition_WithWhitespaceOnlyTitle_ShouldThrowIllegalArgumentException() {
        // Arrange
        PositionRequest request = new PositionRequest("  \t\n  ");

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> positionService.createPosition(request)
        );

        assertEquals("Título do cargo é obrigatório", exception.getMessage());
        verify(positionRepository, never()).existsByTitle(anyString());
        verify(positionRepository, never()).save(any(Position.class));
    }

    @Test
    void getAllPositions_ShouldReturnListOfPositions() {
        // Arrange
        Position position1 = new Position(UUID.randomUUID(), "Desenvolvedor");
        Position position2 = new Position(UUID.randomUUID(), "Analista");
        List<Position> positions = List.of(position1, position2);

        when(positionRepository.findAll()).thenReturn(positions);

        // Act
        List<PositionResponse> responses = positionService.getAllPositions();

        // Assert
        assertNotNull(responses);
        assertEquals(2, responses.size());
        assertEquals("Desenvolvedor", responses.get(0).title());
        assertEquals("Analista", responses.get(1).title());

        verify(positionRepository, times(1)).findAll();
    }

    @Test
    void getAllPositions_WhenNoPositions_ShouldReturnEmptyList() {
        // Arrange
        when(positionRepository.findAll()).thenReturn(List.of());

        // Act
        List<PositionResponse> responses = positionService.getAllPositions();

        // Assert
        assertNotNull(responses);
        assertTrue(responses.isEmpty());
        verify(positionRepository, times(1)).findAll();
    }

    @Test
    void getPositionTitle_WithValidId_ShouldReturnPositionTitle() {
        // Arrange
        UUID positionId = UUID.randomUUID();
        Position position = new Position(positionId, "Desenvolvedor");

        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));

        // Act
        String positionTitle = positionService.getPositionTitle(positionId);

        // Assert
        assertEquals("Desenvolvedor", positionTitle);
        verify(positionRepository, times(1)).findById(positionId);
    }

    @Test
    void getPositionTitle_WithNonExistentId_ShouldReturnNull() {
        // Arrange
        UUID positionId = UUID.randomUUID();

        when(positionRepository.findById(positionId)).thenReturn(Optional.empty());

        // Act
        String positionTitle = positionService.getPositionTitle(positionId);

        // Assert
        assertNull(positionTitle);
        verify(positionRepository, times(1)).findById(positionId);
    }

    @Test
    void getPositionTitle_WithNullId_ShouldReturnNull() {
        // Act
        String positionTitle = positionService.getPositionTitle(null);

        // Assert
        assertNull(positionTitle);
        verify(positionRepository, never()).findById(any(UUID.class));
    }

    @Test
    void toResponse_ShouldConvertPositionToResponseCorrectly() {
        // Arrange
        UUID id = UUID.randomUUID();
        Position position = new Position(id, "Desenvolvedor");

        // Act
        PositionResponse response = positionService.toResponse(position);

        // Assert
        assertNotNull(response);
        assertEquals(id, response.id());
        assertEquals("Desenvolvedor", response.title());
    }

    @Test
    void toResponse_WithNullPosition_ShouldThrowNullPointerException() {
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            positionService.toResponse(null);
        });
    }

    @Test
    void validatePositionTitle_WithValidTitle_ShouldNotThrowException() {
        // Arrange
        when(positionRepository.existsByTitle("Desenvolvedor")).thenReturn(false);
        when(positionRepository.save(any())).thenReturn(new Position(UUID.randomUUID(), "Desenvolvedor"));

        // Act & Assert
        assertDoesNotThrow(() -> {
            positionService.createPosition(new PositionRequest("Desenvolvedor"));
        });
    }

    @Test
    void validatePositionTitle_WithDuplicateTitle_ShouldThrowException() {
        // Arrange
        when(positionRepository.existsByTitle("Desenvolvedor")).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateResourceException.class, () -> {
            positionService.createPosition(new PositionRequest("Desenvolvedor"));
        });
    }

    @Test
    void createPosition_ShouldNotTrimWhitespaceFromTitle() {
        // Arrange
        PositionRequest request = new PositionRequest("  Desenvolvedor  ");

        Position savedPosition = new Position();
        savedPosition.setId(UUID.randomUUID());
        savedPosition.setTitle("  Desenvolvedor  ");

        when(positionRepository.existsByTitle("  Desenvolvedor  ")).thenReturn(false);
        when(positionRepository.save(any(Position.class))).thenReturn(savedPosition);

        // Act
        positionService.createPosition(request);

        // Assert
        verify(positionRepository).existsByTitle("  Desenvolvedor  ");
        verify(positionRepository).save(argThat(position ->
                position.getTitle().equals("  Desenvolvedor  ")
        ));
    }

    @Test
    void getPositionTitle_ShouldReturnTitleWhenPositionExists() {
        // Arrange
        UUID positionId = UUID.randomUUID();
        Position position = new Position(positionId, "Desenvolvedor Sênior");

        when(positionRepository.findById(positionId)).thenReturn(Optional.of(position));

        // Act
        String result = positionService.getPositionTitle(positionId);

        // Assert
        assertEquals("Desenvolvedor Sênior", result);
        verify(positionRepository, times(1)).findById(positionId);
    }

    @Test
    void getPositionTitle_ShouldReturnNullWhenPositionDoesNotExist() {
        // Arrange
        UUID positionId = UUID.randomUUID();

        when(positionRepository.findById(positionId)).thenReturn(Optional.empty());

        // Act
        String result = positionService.getPositionTitle(positionId);

        // Assert
        assertNull(result);
        verify(positionRepository, times(1)).findById(positionId);
    }
}