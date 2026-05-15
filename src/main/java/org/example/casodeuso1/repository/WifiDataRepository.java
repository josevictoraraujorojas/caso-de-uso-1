package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.WifiData;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WifiDataRepository {
    void salvar(WifiData wifiData);

    List<WifiData> listar();

    List<WifiData> buscarPorEsp(Long idEsp);

    List<WifiData> buscarPorLab(Long idLab);

    List<WifiData> buscarPorPredio(Long idPredio);

    List<WifiData> buscarPorPontoAcesso(Long idPontoAcesso);

    List<WifiData> buscarPorDispositivoWifi(Long idDispositivoWifi);

    void remover(Long idEsp);


}
