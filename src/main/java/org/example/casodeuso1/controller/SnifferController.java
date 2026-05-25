package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.SnifferCreateDTO;
import org.example.casodeuso1.dto.SnifferResponseDTO;
import org.example.casodeuso1.service.SnifferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sniffer")
public class SnifferController {

    private final SnifferService snifferService;

    public SnifferController(SnifferService snifferService) {
        this.snifferService = snifferService;
    }

    @PostMapping
    public ResponseEntity<SnifferResponseDTO> criar(
            @RequestBody SnifferCreateDTO snifferCreateDTO) {

        SnifferResponseDTO response =
                snifferService.salvar(snifferCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{snifferId}")
    public ResponseEntity<SnifferResponseDTO> atualizar(
            @PathVariable Long snifferId,
            @RequestBody SnifferCreateDTO snifferCreateDTO) {

        SnifferResponseDTO response =
                snifferService.editar(snifferId, snifferCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{snifferId}/dispositivoWifi/{dispositivoWifiId}")
    public ResponseEntity<SnifferResponseDTO> adicionarDispositivoWifi(
            @PathVariable Long snifferId,
            @PathVariable Long dispositivoWifiId) {

        return ResponseEntity.ok(
                snifferService.adicionarDispositivoWifi(
                        snifferId,
                        dispositivoWifiId
                )
        );
    }

    @PutMapping("/{snifferId}/pontoAcesso/{pontoAcessoId}")
    public ResponseEntity<SnifferResponseDTO> adicionarPontoAcesso(
            @PathVariable Long snifferId,
            @PathVariable Long pontoAcessoId) {

        return ResponseEntity.ok(
                snifferService.adicionarPontoAcesso(
                        snifferId,
                        pontoAcessoId
                )
        );
    }

    @GetMapping("/{snifferId}")
    public ResponseEntity<SnifferResponseDTO> buscarPorId(
            @PathVariable Long snifferId) {

        SnifferResponseDTO response =
                snifferService.buscarPorId(snifferId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<SnifferResponseDTO>> listar() {
        return ResponseEntity.ok(snifferService.listar());
    }

    @DeleteMapping("/{snifferId}/dispositivoWifi/{dispositivoWifiId}")
    public ResponseEntity<SnifferResponseDTO> removerDispositivoWifi(
            @PathVariable Long snifferId,
            @PathVariable Long dispositivoWifiId) {

        return ResponseEntity.ok(
                snifferService.removerDispositivoWifi(
                        snifferId,
                        dispositivoWifiId
                )
        );
    }

    @DeleteMapping("/{snifferId}/pontoAcesso/{pontoAcessoId}")
    public ResponseEntity<SnifferResponseDTO> removerPontoAcesso(
            @PathVariable Long snifferId,
            @PathVariable Long pontoAcessoId) {

        return ResponseEntity.ok(
                snifferService.removerPontoAcesso(
                        snifferId,
                        pontoAcessoId
                )
        );
    }

    @DeleteMapping("/{snifferId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long snifferId) {

        snifferService.excluir(snifferId);
        return ResponseEntity.noContent().build();
    }
}