package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.PontoAcessoCreateDTO;
import org.example.casodeuso1.dto.PontoAcessoResponseDTO;
import org.example.casodeuso1.service.PontoAcessoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pontoAcesso")
public class PontoAcessoController {

    private final PontoAcessoService pontoAcessoService;

    public PontoAcessoController(PontoAcessoService pontoAcessoService) {
        this.pontoAcessoService = pontoAcessoService;
    }

    @PostMapping
    public ResponseEntity<PontoAcessoResponseDTO> criar(
            @RequestBody PontoAcessoCreateDTO pontoAcessoCreateDTO) {

        PontoAcessoResponseDTO response =
                pontoAcessoService.salvar(pontoAcessoCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{pontoAcessoId}")
    public ResponseEntity<PontoAcessoResponseDTO> atualizar(
            @PathVariable Long pontoAcessoId,
            @RequestBody PontoAcessoCreateDTO pontoAcessoCreateDTO) {

        PontoAcessoResponseDTO response =
                pontoAcessoService.editar(pontoAcessoId, pontoAcessoCreateDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{pontoAcessoId}")
    public ResponseEntity<PontoAcessoResponseDTO> buscarPorId(
            @PathVariable Long pontoAcessoId) {

        PontoAcessoResponseDTO response =
                pontoAcessoService.buscarPorId(pontoAcessoId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<PontoAcessoResponseDTO>> listar() {
        return ResponseEntity.ok(pontoAcessoService.listar());
    }

    @DeleteMapping("/{pontoAcessoId}")
    public ResponseEntity<Void> deletar(
            @PathVariable Long pontoAcessoId) {

        pontoAcessoService.excluir(pontoAcessoId);
        return ResponseEntity.noContent().build();
    }
}