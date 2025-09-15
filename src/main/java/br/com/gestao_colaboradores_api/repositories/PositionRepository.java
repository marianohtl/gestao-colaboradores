package br.com.gestao_colaboradores_api.repositories;

import br.com.gestao_colaboradores_api.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PositionRepository extends JpaRepository<Position, UUID> {

    boolean existsByTitle(String title);
}