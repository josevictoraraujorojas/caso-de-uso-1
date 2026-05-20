package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.MonitorEnergiaCreateDTO;
import org.example.casodeuso1.dto.MonitorEnergiaResponseDTO;
import org.example.casodeuso1.service.MonitorEnergiaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/monitorEnergia")
public class MonitorEnergiaController {

    private final MonitorEnergiaService monitorEnergiaService;

    public MonitorEnergiaController(
            MonitorEnergiaService monitorEnergiaService) {

        this.monitorEnergiaService = monitorEnergiaService;
    }

    @PostMapping
    public ResponseEntity<MonitorEnergiaResponseDTO> criar(
            @RequestBody MonitorEnergiaCreateDTO monitorEnergiaCreateDTO) {

        MonitorEnergiaResponseDTO response =
                monitorEnergiaService.salvar(monitorEnergiaCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{monitorEnergiaId}")
    public ResponseEntity<MonitorEnergiaResponseDTO> atualizar(
            @PathVariable Long monitorEnergiaId,
            @RequestBody MonitorEnergiaCreateDTO monitorEnergiaCreateDTO) {

        MonitorEnergiaResponseDTO response =
                monitorEnergiaService.editar(
                        monitorEnergiaId,
                        monitorEnergiaCreateDTO
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{monitorEnergiaId}")
    public ResponseEntity<MonitorEnergiaResponseDTO> buscarPorId(
            @PathVariable Long monitorEnergiaId) {

        MonitorEnergiaResponseDTO response =
                monitorEnergiaService.buscarPorId(monitorEnergiaId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MonitorEnergiaResponseDTO>> listar() {

        return ResponseEntity.ok(
                monitorEnergiaService.listar()
        );
    }

    @DeleteMapping("/{monitorEnergiaId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long monitorEnergiaId) {

        monitorEnergiaService.excluir(monitorEnergiaId);

        return ResponseEntity.noContent().build();
    }
}