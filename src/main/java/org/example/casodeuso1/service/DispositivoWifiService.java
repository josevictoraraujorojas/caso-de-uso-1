package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.DispositivoWifiCreateDTO;
import org.example.casodeuso1.dto.DispositivoWifiResponseDTO;
import org.example.casodeuso1.model.DispositivoWifi;
import org.example.casodeuso1.repository.DispositivoWifiRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DispositivoWifiService {

    private final DispositivoWifiRepository dispositivoWifiRepository;

    @Autowired
    public DispositivoWifiService(DispositivoWifiRepository dispositivoWifiRepository) {
        this.dispositivoWifiRepository = dispositivoWifiRepository;
    }

    public DispositivoWifiResponseDTO salvar(DispositivoWifiCreateDTO dispositivoWifiCreateDTO) {
        if (dispositivoWifiCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do dispositivo Wi-Fi inválidos"
            );
        }

        DispositivoWifi dispositivoWifi = DataMapper.parseObject(
                dispositivoWifiCreateDTO,
                DispositivoWifi.class
        );

        return DataMapper.parseObject(
                dispositivoWifiRepository.save(dispositivoWifi),
                DispositivoWifiResponseDTO.class
        );
    }

    public DispositivoWifiResponseDTO editar(Long dispositivoId,
                                             DispositivoWifiCreateDTO dispositivoWifiCreateDTO) {

        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dispositivo Wi-Fi não encontrado"
                ));

        dispositivoWifi.setEnderecoMac(dispositivoWifiCreateDTO.getEnderecoMac());

        return DataMapper.parseObject(
                dispositivoWifiRepository.save(dispositivoWifi),
                DispositivoWifiResponseDTO.class
        );
    }

    public DispositivoWifiResponseDTO buscarPorId(Long dispositivoId) {
        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dispositivo Wi-Fi não encontrado"
                ));

        return DataMapper.parseObject(
                dispositivoWifi,
                DispositivoWifiResponseDTO.class
        );
    }

    public List<DispositivoWifiResponseDTO> listar() {
        List<DispositivoWifi> dispositivos = dispositivoWifiRepository.findAll();

        if (dispositivos.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dispositivo Wi-Fi encontrado"
            );
        }

        return DataMapper.parseListObjects(
                dispositivos,
                DispositivoWifiResponseDTO.class
        );
    }

    public void excluir(Long dispositivoId) {
        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dispositivo Wi-Fi não encontrado"
                ));

        dispositivoWifiRepository.delete(dispositivoWifi);
    }
}