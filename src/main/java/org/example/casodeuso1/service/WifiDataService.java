package org.example.casodeuso1.service;

import org.example.casodeuso1.model.WifiData;
import org.example.casodeuso1.repository.WifiDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class WifiDataService {

    private final WifiDataRepository wifiDataRepository;

    @Autowired
    public WifiDataService(WifiDataRepository wifiDataRepository) {
        this.wifiDataRepository = wifiDataRepository;
    }

    public void salvar(WifiData wifiData) {

        if (wifiData == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do Wi-Fi inválidos"
            );
        }

        wifiDataRepository.salvar(wifiData);
    }

    public List<WifiData> listar() {

        List<WifiData> wifiDataList = wifiDataRepository.listar();

        if (wifiDataList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado Wi-Fi encontrado"
            );
        }

        return wifiDataList;
    }

    public List<WifiData> buscarPorEsp(Long idEsp) {

        List<WifiData> wifiDataList =
                wifiDataRepository.buscarPorEsp(idEsp);

        if (wifiDataList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o ESP"
            );
        }

        return wifiDataList;
    }

    public List<WifiData> buscarPorLab(Long idLab) {

        List<WifiData> wifiDataList =
                wifiDataRepository.buscarPorLab(idLab);

        if (wifiDataList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o laboratório"
            );
        }

        return wifiDataList;
    }

    public List<WifiData> buscarPorPredio(Long idPredio) {

        List<WifiData> wifiDataList =
                wifiDataRepository.buscarPorPredio(idPredio);

        if (wifiDataList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o prédio"
            );
        }

        return wifiDataList;
    }

    public List<WifiData> buscarPorPontoAcesso(Long idPontoAcesso) {

        List<WifiData> wifiDataList =
                wifiDataRepository.buscarPorPontoAcesso(idPontoAcesso);

        if (wifiDataList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o ponto de acesso"
            );
        }

        return wifiDataList;
    }

    public List<WifiData> buscarPorDispositivoWifi(Long idDispositivoWifi) {

        List<WifiData> wifiDataList =
                wifiDataRepository.buscarPorDispositivoWifi(idDispositivoWifi);

        if (wifiDataList.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o dispositivo Wi-Fi"
            );
        }

        return wifiDataList;
    }

    public void remover(Long idEsp) {

        wifiDataRepository.remover(idEsp);
    }
}