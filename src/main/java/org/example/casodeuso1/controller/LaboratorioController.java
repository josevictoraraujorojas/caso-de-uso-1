package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.LaboratorioCreateDTO;
import org.example.casodeuso1.dto.LaboratorioResponseDTO;
import org.example.casodeuso1.service.LaboratorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/laboratorio")
public class LaboratorioController {

    private final LaboratorioService laboratorioService;

    @Autowired
    public LaboratorioController(LaboratorioService laboratorioService) {
        this.laboratorioService = laboratorioService;
    }

    @PostMapping
    public ResponseEntity<LaboratorioResponseDTO> criar(
            @RequestBody LaboratorioCreateDTO laboratorioCreateDTO) {

        LaboratorioResponseDTO response =
                laboratorioService.salvar(laboratorioCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{laboratorioId}")
    public ResponseEntity<LaboratorioResponseDTO> atualizar(
            @PathVariable Long laboratorioId,
            @RequestBody LaboratorioCreateDTO laboratorioCreateDTO) {

        LaboratorioResponseDTO response =
                laboratorioService.editar(laboratorioId, laboratorioCreateDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{laboratorioId}")
    public ResponseEntity<LaboratorioResponseDTO> buscarPorId(
            @PathVariable Long laboratorioId) {

        LaboratorioResponseDTO response =
                laboratorioService.buscarPorId(laboratorioId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<LaboratorioResponseDTO>> listar() {
        return ResponseEntity.ok(laboratorioService.listar());
    }

    @DeleteMapping("/{laboratorioId}")
    public ResponseEntity<Void> deletar(@PathVariable Long laboratorioId) {
        laboratorioService.excluir(laboratorioId);
        return ResponseEntity.noContent().build();
    }
}