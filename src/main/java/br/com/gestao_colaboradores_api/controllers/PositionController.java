package br.com.gestao_colaboradores_api.controllers;

import br.com.gestao_colaboradores_api.dtos.PositionRequest;
import br.com.gestao_colaboradores_api.dtos.PositionResponse;
import br.com.gestao_colaboradores_api.services.PositionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@Tag(name = "Positions", description = "Gestão de cargos")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @PostMapping
    @Operation(summary = "Criar novo cargo")
    public ResponseEntity<PositionResponse> create(@Valid @RequestBody PositionRequest request) {
        PositionResponse response = positionService.createPosition(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @Operation(summary = "Listar todos os cargos")
    public ResponseEntity<List<PositionResponse>> getAll() {
        List<PositionResponse> positions = positionService.getAllPositions();
        return ResponseEntity.ok(positions);
    }
}