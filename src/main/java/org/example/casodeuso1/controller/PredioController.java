package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.PredioCreateDTO;
import org.example.casodeuso1.dto.PredioResponseDTO;
import org.example.casodeuso1.service.PredioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/predio")
public class PredioController {

    private final PredioService predioService;

    public PredioController(PredioService predioService) {
        this.predioService = predioService;
    }

    @PostMapping
    public ResponseEntity<PredioResponseDTO> criar(
            @RequestBody PredioCreateDTO predioCreateDTO) {

        PredioResponseDTO response =
                predioService.salvar(predioCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{predioId}")
    public ResponseEntity<PredioResponseDTO> atualizar(
            @PathVariable Long predioId,
            @RequestBody PredioCreateDTO predioCreateDTO) {

        PredioResponseDTO response =
                predioService.editar(predioId, predioCreateDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{predioId}")
    public ResponseEntity<PredioResponseDTO> buscarPorId(
            @PathVariable Long predioId) {

        PredioResponseDTO response =
                predioService.buscarPorId(predioId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PredioResponseDTO>> listar() {
        return ResponseEntity.ok(predioService.listar());
    }

    @DeleteMapping("/{predioId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long predioId) {

        predioService.excluir(predioId);

        return ResponseEntity.noContent().build();
    }
}