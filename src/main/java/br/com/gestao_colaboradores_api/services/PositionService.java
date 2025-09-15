package br.com.gestao_colaboradores_api.services;

import br.com.gestao_colaboradores_api.dtos.PositionRequest;
import br.com.gestao_colaboradores_api.dtos.PositionResponse;
import br.com.gestao_colaboradores_api.exceptions.DuplicateResourceException;
import br.com.gestao_colaboradores_api.models.Position;
import br.com.gestao_colaboradores_api.repositories.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PositionService {
    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public PositionResponse createPosition(PositionRequest request) {
        validatePositionTitle(request.title());

        Position position = new Position();
        position.setTitle(request.title());

        Position savedPosition = positionRepository.save(position);
        return toResponse(savedPosition);
    }
    public List<PositionResponse> getAllPositions() {
        return positionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public String getPositionTitle(UUID positionId) {
        if (positionId == null) {
            return null;
        }

        return positionRepository.findById(positionId)
                .map(Position::getTitle)
                .orElse(null);
    }

    private void validatePositionTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Título do cargo é obrigatório");
        }

        if (positionRepository.existsByTitle(title)) {
            throw new DuplicateResourceException("Cargo", title);
        }
    }
    protected PositionResponse toResponse(Position position) {
        return new PositionResponse(
                position.getId(),
                position.getTitle()
        );
    }
}

