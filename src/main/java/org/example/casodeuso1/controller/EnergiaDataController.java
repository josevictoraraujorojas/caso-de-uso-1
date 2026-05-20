package org.example.casodeuso1.controller;

import org.example.casodeuso1.model.EnergiaData;
import org.example.casodeuso1.service.EnergiaDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/energiaData")
public class EnergiaDataController {

    private final EnergiaDataService energiaDataService;

    public EnergiaDataController(EnergiaDataService energiaDataService) {
        this.energiaDataService = energiaDataService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(
            @RequestBody EnergiaData energiaData) {

        energiaDataService.salvar(energiaData);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<EnergiaData>> listar() {

        return ResponseEntity.ok(
                energiaDataService.listar()
        );
    }

    @GetMapping("/aparelho/{idAparelho}")
    public ResponseEntity<List<EnergiaData>> buscarPorAparelho(
            @PathVariable Long idAparelho) {

        return ResponseEntity.ok(
                energiaDataService.buscarPorAparelho(idAparelho)
        );
    }

    @GetMapping("/esp/{idEsp}")
    public ResponseEntity<List<EnergiaData>> buscarPorEsp(
            @PathVariable Long idEsp) {

        return ResponseEntity.ok(
                energiaDataService.buscarPorEsp(idEsp)
        );
    }

    @GetMapping("/filtro/{idFiltro}")
    public ResponseEntity<List<EnergiaData>> buscarPorFiltro(
            @PathVariable Long idFiltro) {

        return ResponseEntity.ok(
                energiaDataService.buscarPorFiltro(idFiltro)
        );
    }

    @GetMapping("/laboratorio/{idLab}")
    public ResponseEntity<List<EnergiaData>> buscarPorLab(
            @PathVariable Long idLab) {

        return ResponseEntity.ok(
                energiaDataService.buscarPorLab(idLab)
        );
    }

    @GetMapping("/predio/{idPredio}")
    public ResponseEntity<List<EnergiaData>> buscarPorPredio(
            @PathVariable Long idPredio) {

        return ResponseEntity.ok(
                energiaDataService.buscarPorPredio(idPredio)
        );
    }

    @DeleteMapping("/{idEsp}")
    public ResponseEntity<Void> remover(
            @PathVariable Long idEsp) {

        energiaDataService.remover(idEsp);

        return ResponseEntity.noContent().build();
    }
}