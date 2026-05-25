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
    @PutMapping("/{dispositivoRemetenteId}/pontoAcesso/{pontoAcessoDestinatarioId}")
    public ResponseEntity<DispositivoWifiResponseDTO> adicionarPontoAcesso(@PathVariable Long dispositivoRemetenteId , @PathVariable Long pontoAcessoDestinatarioId) {
        DispositivoWifiResponseDTO response = dispositivoWifiService.adicionarPontoAcesso(dispositivoRemetenteId, pontoAcessoDestinatarioId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{dispositivoRemetenteId}/dispositivoWifi/{dispositivoDestinatarioId}")
    public ResponseEntity<DispositivoWifiResponseDTO> adicionarDispositivoWifi(@PathVariable Long dispositivoRemetenteId , @PathVariable Long dispositivoDestinatarioId) {
        DispositivoWifiResponseDTO response = dispositivoWifiService.adicionarDispositivoWifi(dispositivoRemetenteId, dispositivoDestinatarioId);
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

    @DeleteMapping("/{dispositivoRemetenteId}/pontoAcesso/{pontoAcessoDestinatarioId}")
    public ResponseEntity<DispositivoWifiResponseDTO> removerPontoAcesso(@PathVariable Long dispositivoRemetenteId, @PathVariable Long pontoAcessoDestinatarioId) {
        return ResponseEntity.ok(
                dispositivoWifiService.removerPontoAcesso(dispositivoRemetenteId, pontoAcessoDestinatarioId)
        );
    }

    @DeleteMapping("/{dispositivoRemetenteId}/dispositivoWifi/{dispositivoDestinatarioId}")
    public ResponseEntity<DispositivoWifiResponseDTO> removerDispositivoWifi(@PathVariable Long dispositivoRemetenteId, @PathVariable Long dispositivoDestinatarioId) {
        return ResponseEntity.ok(
                dispositivoWifiService.removerDispositivoWifi(dispositivoRemetenteId, dispositivoDestinatarioId)
        );
    }

    @DeleteMapping("/{dispositivoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long dispositivoId) {
        dispositivoWifiService.excluir(dispositivoId);
        return ResponseEntity.noContent().build();
    }
}