package org.example.casodeuso1.controller;

import org.example.casodeuso1.model.WifiData;
import org.example.casodeuso1.service.WifiDataService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wifiData")
public class WifiDataController {

    private final WifiDataService wifiDataService;

    public WifiDataController(WifiDataService wifiDataService) {
        this.wifiDataService = wifiDataService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(
            @RequestBody WifiData wifiData) {

        wifiDataService.salvar(wifiData);

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<WifiData>> listar() {

        return ResponseEntity.ok(
                wifiDataService.listar()
        );
    }

    @GetMapping("/esp/{idEsp}")
    public ResponseEntity<List<WifiData>> buscarPorEsp(
            @PathVariable Long idEsp) {

        return ResponseEntity.ok(
                wifiDataService.buscarPorEsp(idEsp)
        );
    }

    @GetMapping("/laboratorio/{idLab}")
    public ResponseEntity<List<WifiData>> buscarPorLab(
            @PathVariable Long idLab) {

        return ResponseEntity.ok(
                wifiDataService.buscarPorLab(idLab)
        );
    }

    @GetMapping("/predio/{idPredio}")
    public ResponseEntity<List<WifiData>> buscarPorPredio(
            @PathVariable Long idPredio) {

        return ResponseEntity.ok(
                wifiDataService.buscarPorPredio(idPredio)
        );
    }

    @GetMapping("/pontoAcesso/{idPontoAcesso}")
    public ResponseEntity<List<WifiData>> buscarPorPontoAcesso(
            @PathVariable Long idPontoAcesso) {

        return ResponseEntity.ok(
                wifiDataService.buscarPorPontoAcesso(idPontoAcesso)
        );
    }

    @GetMapping("/dispositivoWifi/{idDispositivoWifi}")
    public ResponseEntity<List<WifiData>> buscarPorDispositivoWifi(
            @PathVariable Long idDispositivoWifi) {

        return ResponseEntity.ok(
                wifiDataService.buscarPorDispositivoWifi(idDispositivoWifi)
        );
    }

    @DeleteMapping("/{idEsp}")
    public ResponseEntity<Void> remover(
            @PathVariable Long idEsp) {

        wifiDataService.remover(idEsp);

        return ResponseEntity.noContent().build();
    }
}