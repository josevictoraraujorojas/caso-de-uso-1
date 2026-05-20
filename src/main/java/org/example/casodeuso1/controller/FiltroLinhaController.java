package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.FiltroLinhaCreateDTO;
import org.example.casodeuso1.dto.FiltroLinhaResponseDTO;
import org.example.casodeuso1.service.FiltroLinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/filtroLinha")
public class FiltroLinhaController {

    private final FiltroLinhaService filtroLinhaService;

    @Autowired
    public FiltroLinhaController(FiltroLinhaService filtroLinhaService) {
        this.filtroLinhaService = filtroLinhaService;
    }

    @PostMapping
    public ResponseEntity<FiltroLinhaResponseDTO> criar(
            @RequestBody FiltroLinhaCreateDTO filtroLinhaCreateDTO) {

        FiltroLinhaResponseDTO response =
                filtroLinhaService.salvar(filtroLinhaCreateDTO);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{filtroLinhaId}")
    public ResponseEntity<FiltroLinhaResponseDTO> atualizar(
            @PathVariable Long filtroLinhaId,
            @RequestBody FiltroLinhaCreateDTO filtroLinhaCreateDTO) {

        FiltroLinhaResponseDTO response =
                filtroLinhaService.editar(filtroLinhaId, filtroLinhaCreateDTO);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{filtroLinhaId}")
    public ResponseEntity<FiltroLinhaResponseDTO> buscarPorId(
            @PathVariable Long filtroLinhaId) {

        FiltroLinhaResponseDTO response =
                filtroLinhaService.buscarPorId(filtroLinhaId);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FiltroLinhaResponseDTO>> listar() {
        return ResponseEntity.ok(filtroLinhaService.listar());
    }

    @DeleteMapping("/{filtroLinhaId}")
    public ResponseEntity<Void> deletar(@PathVariable Long filtroLinhaId) {
        filtroLinhaService.excluir(filtroLinhaId);
        return ResponseEntity.noContent().build();
    }
}