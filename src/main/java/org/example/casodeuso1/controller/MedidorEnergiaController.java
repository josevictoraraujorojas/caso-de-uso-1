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

    @PutMapping("/{medidorEnergiaId}/monitorEnergia/{monitorEnergiaId}")
    public ResponseEntity<MedidorEnergiaResponseDTO> adicionarMonitorEnergia(
            @PathVariable Long medidorEnergiaId,
            @PathVariable Long monitorEnergiaId) {

        return ResponseEntity.ok(
                medidorEnergiaService.adicionarMonitorEnergia(
                        medidorEnergiaId,
                        monitorEnergiaId
                )
        );
    }

    @PutMapping("/{medidorEnergiaId}/aparelho/{aparelhoId}")
    public ResponseEntity<MedidorEnergiaResponseDTO> adicionarAparelho(
            @PathVariable Long medidorEnergiaId,
            @PathVariable Long aparelhoId) {

        return ResponseEntity.ok(
                medidorEnergiaService.adicionarAparelho(
                        medidorEnergiaId,
                        aparelhoId
                )
        );
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

    @DeleteMapping("/{medidorEnergiaId}/monitorEnergia")
    public ResponseEntity<MedidorEnergiaResponseDTO> removerMonitorEnergia(
            @PathVariable Long medidorEnergiaId) {

        return ResponseEntity.ok(
                medidorEnergiaService.removerMonitorEnergia(
                        medidorEnergiaId
                )
        );
    }

    @DeleteMapping("/{medidorEnergiaId}/aparelho/{aparelhoId}")
    public ResponseEntity<MedidorEnergiaResponseDTO> removerAparelho(
            @PathVariable Long medidorEnergiaId,
            @PathVariable Long aparelhoId) {

        return ResponseEntity.ok(
                medidorEnergiaService.removerAparelho(
                        medidorEnergiaId,
                        aparelhoId
                )
        );
    }

    @DeleteMapping("/{medidorEnergiaId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long medidorEnergiaId) {

        medidorEnergiaService.excluir(medidorEnergiaId);

        return ResponseEntity.noContent().build();
    }
}