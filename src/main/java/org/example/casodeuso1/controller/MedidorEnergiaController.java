package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.MedidorEnergiaCreateDTO;
import org.example.casodeuso1.dto.MedidorEnergiaResponseDTO;
import org.example.casodeuso1.service.MedidorEnergiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medidorEnergia")
public class MedidorEnergiaController {

    private final MedidorEnergiaService medidorEnergiaService;

    @Autowired
    public MedidorEnergiaController(MedidorEnergiaService medidorEnergiaService) {
        this.medidorEnergiaService = medidorEnergiaService;
    }

    @PostMapping
    public ResponseEntity<MedidorEnergiaResponseDTO> criar(
            @RequestBody MedidorEnergiaCreateDTO medidorEnergiaCreateDTO) {

        MedidorEnergiaResponseDTO response =
                medidorEnergiaService.salvar(medidorEnergiaCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{medidorEnergiaId}")
    public ResponseEntity<MedidorEnergiaResponseDTO> atualizar(
            @PathVariable Long medidorEnergiaId,
            @RequestBody MedidorEnergiaCreateDTO medidorEnergiaCreateDTO) {

        MedidorEnergiaResponseDTO response =
                medidorEnergiaService.editar(
                        medidorEnergiaId,
                        medidorEnergiaCreateDTO
                );

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{medidorEnergiaId}")
    public ResponseEntity<MedidorEnergiaResponseDTO> buscarPorId(
            @PathVariable Long medidorEnergiaId) {

        MedidorEnergiaResponseDTO response =
                medidorEnergiaService.buscarPorId(medidorEnergiaId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<MedidorEnergiaResponseDTO>> listar() {

        return ResponseEntity.ok(
                medidorEnergiaService.listar()
        );
    }

    @DeleteMapping("/{medidorEnergiaId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long medidorEnergiaId) {

        medidorEnergiaService.excluir(medidorEnergiaId);

        return ResponseEntity.noContent().build();
    }
}