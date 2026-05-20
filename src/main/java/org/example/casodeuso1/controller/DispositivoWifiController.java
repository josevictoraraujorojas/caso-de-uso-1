package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.DispositivoWifiCreateDTO;
import org.example.casodeuso1.dto.DispositivoWifiResponseDTO;
import org.example.casodeuso1.service.DispositivoWifiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/dispositivoWifi")
public class DispositivoWifiController {

    private final DispositivoWifiService dispositivoWifiService;

    @Autowired
    public DispositivoWifiController(DispositivoWifiService dispositivoWifiService) {
        this.dispositivoWifiService = dispositivoWifiService;
    }

    @PostMapping
    public ResponseEntity<DispositivoWifiResponseDTO> criar(
            @RequestBody DispositivoWifiCreateDTO dispositivoWifiCreateDTO) {

        DispositivoWifiResponseDTO response =
                dispositivoWifiService.salvar(dispositivoWifiCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{dispositivoId}")
    public ResponseEntity<DispositivoWifiResponseDTO> atualizar(
            @PathVariable Long dispositivoId,
            @RequestBody DispositivoWifiCreateDTO dispositivoWifiCreateDTO) {

        DispositivoWifiResponseDTO response =
                dispositivoWifiService.editar(dispositivoId, dispositivoWifiCreateDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{dispositivoId}")
    public ResponseEntity<DispositivoWifiResponseDTO> buscarPorId(
            @PathVariable Long dispositivoId) {

        DispositivoWifiResponseDTO response =
                dispositivoWifiService.buscarPorId(dispositivoId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<DispositivoWifiResponseDTO>> listar() {
        return ResponseEntity.ok(dispositivoWifiService.listar());
    }

    @DeleteMapping("/{dispositivoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long dispositivoId) {
        dispositivoWifiService.excluir(dispositivoId);
        return ResponseEntity.noContent().build();
    }
}