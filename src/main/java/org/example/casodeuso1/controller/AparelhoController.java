package org.example.casodeuso1.controller;

import org.example.casodeuso1.dto.AparelhoCreateDTO;
import org.example.casodeuso1.dto.AparelhoResponseDTO;
import org.example.casodeuso1.service.AparelhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aparelho")
public class AparelhoController {
    private final AparelhoService aparelhoService;

    @Autowired
    public AparelhoController(AparelhoService aparelhoService) {
        this.aparelhoService = aparelhoService;
    }


    @PostMapping
    public ResponseEntity<AparelhoResponseDTO> criar(@RequestBody AparelhoCreateDTO aparelhoCreateDTO){
        AparelhoResponseDTO aparelhoResponseDTO = aparelhoService.salvar(aparelhoCreateDTO);
        return ResponseEntity.ok(aparelhoResponseDTO);
    }

    @PutMapping("/{aparelhoId}")
    public ResponseEntity<AparelhoResponseDTO> atualizar(@PathVariable Long aparelhoId, @RequestBody AparelhoCreateDTO aparelhoCreateDTO) {
        AparelhoResponseDTO response = aparelhoService.editar(aparelhoId, aparelhoCreateDTO);
        return ResponseEntity.ok(response);
    }

    // GET por ID
    @GetMapping("/{aparelhoId}")
    public ResponseEntity<AparelhoResponseDTO> buscarPorId(@PathVariable Long aparelhoId) {
        AparelhoResponseDTO response = aparelhoService.buscarPorId(aparelhoId);
        return ResponseEntity.ok(response);
    }

    // GET todos
    @GetMapping
    public ResponseEntity<List<AparelhoResponseDTO>> listar() {
        return ResponseEntity.ok(aparelhoService.listar());
    }

    // DELETE
    @DeleteMapping("/{aparelhoId}")
    public ResponseEntity<Void> deletar(@PathVariable Long aparelhoId) {
        aparelhoService.excluir(aparelhoId);
        return ResponseEntity.noContent().build();
    }
}
