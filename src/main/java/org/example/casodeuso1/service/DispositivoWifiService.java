package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.DispositivoWifiCreateDTO;
import org.example.casodeuso1.dto.DispositivoWifiResponseDTO;
import org.example.casodeuso1.model.DispositivoWifi;
import org.example.casodeuso1.model.MonitorEnergia;
import org.example.casodeuso1.model.PontoAcesso;
import org.example.casodeuso1.repository.DispositivoWifiRepository;
import org.example.casodeuso1.repository.PontoAcessoRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class DispositivoWifiService {

    private final DispositivoWifiRepository dispositivoWifiRepository;
    private final PontoAcessoRepository pontoAcessoRepository;

    @Autowired
    public DispositivoWifiService(DispositivoWifiRepository dispositivoWifiRepository, PontoAcessoRepository pontoAcessoRepository) {
        this.dispositivoWifiRepository = dispositivoWifiRepository;
        this.pontoAcessoRepository = pontoAcessoRepository;
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

    public DispositivoWifiResponseDTO adicionarPontoAcesso(Long dispositivoRemetenteId, Long pontoAcessoDestinatarioId){
        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoRemetenteId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Dispositivo Wifi Remetente não encontrado"
        ));
        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoDestinatarioId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ponto de Acesso Destinatario não encontrado"
        ));

        if (dispositivoWifi.getDispositivosWifi()==null){
            dispositivoWifi.setDispositivosWifi(new HashSet<>());
        }
        if (dispositivoWifi.getPontosAcessoEnvia().contains(pontoAcesso)){
           return null;
        }
        dispositivoWifi.getPontosAcessoEnvia().add(pontoAcesso);
        return DataMapper.parseObject(dispositivoWifiRepository.save(dispositivoWifi), DispositivoWifiResponseDTO.class);
    }

    public DispositivoWifiResponseDTO adicionarDispositivoWifi(Long dispositivoRemetenteId, Long dispositivoDestinatarioId){
        DispositivoWifi dispositivoWifiRemetente = dispositivoWifiRepository.findById(dispositivoRemetenteId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Dispositivo Remetente Wifi não encontrado"
        ));

        DispositivoWifi dispositivoWifiDestinatario = dispositivoWifiRepository.findById(dispositivoDestinatarioId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Dispositivo Destinatario Wifi não encontrado"
        ));


        if (dispositivoWifiRemetente.getPontosAcessoEnvia()==null){
            dispositivoWifiRemetente.setPontosAcessoEnvia(new HashSet<>());
        }
        if (dispositivoWifiRemetente.equals(dispositivoWifiDestinatario)){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Os Dispositivos são iguais");
        }
        if (dispositivoWifiRemetente.getDispositivosWifi().contains(dispositivoWifiDestinatario)){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse Dispositivo de Destino já está vinculado ao Dispositivo Remetente"
            );
        }
        dispositivoWifiRemetente.getDispositivosWifi().add(dispositivoWifiDestinatario);
        return DataMapper.parseObject(dispositivoWifiRepository.save(dispositivoWifiRemetente), DispositivoWifiResponseDTO.class);
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

    // BUSCAR POR MAC
    public DispositivoWifiResponseDTO buscarPorMac(String mac) {

        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findByEnderecoMac(mac);

        if (dispositivoWifi == null) {
        return null;
        }


        return DataMapper.parseObject(dispositivoWifi, DispositivoWifiResponseDTO.class);
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

    public DispositivoWifiResponseDTO removerPontoAcesso(Long dispositivoRemetenteId, Long pontoAcessoDestinatarioId) {

        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoRemetenteId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Dispositivo Wifi Remetente não encontrado"
        ));
        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoDestinatarioId).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Ponto de Acesso Destinatario não encontrado"
        ));

        if (dispositivoWifi.getPontosAcessoEnvia() == null || dispositivoWifi.getPontosAcessoEnvia().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse Dispositivo Wifi não possui Pontos de Acesso"
            );
        }

        if (!dispositivoWifi.getPontosAcessoEnvia().contains(pontoAcesso)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse Ponto de Acesso de Destino não está vinculado a esse Dispositivo Remetente"
            );
        }

        dispositivoWifi.getPontosAcessoEnvia().remove(pontoAcesso);

        return DataMapper.parseObject(
                dispositivoWifiRepository.save(dispositivoWifi),
                DispositivoWifiResponseDTO.class
        );
    }

    public DispositivoWifiResponseDTO removerDispositivoWifi(Long dispositivoRemetenteId, Long dispositivoDestinatarioId) {

        DispositivoWifi dispositivoWifiRemetente = dispositivoWifiRepository.findById(dispositivoRemetenteId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Dispositivo Wifi Remetente não encontrado"
        ));
        DispositivoWifi dispositivoWifiDestinatario = dispositivoWifiRepository.findById(dispositivoDestinatarioId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Dispositivo Wifi Destinatario não encontrado"
        ));

        if (dispositivoWifiRemetente.getDispositivosWifi() == null || dispositivoWifiRemetente.getDispositivosWifi().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse Dispositivo Wifi remetente não possui Dispositivos Wifi destinatarios"
            );
        }

        if (!dispositivoWifiRemetente.getDispositivosWifi().contains(dispositivoWifiDestinatario)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse Dispositivo de destinatario não está vinculado a esse Dispositivo remetente"
            );
        }

        dispositivoWifiRemetente.getDispositivosWifi().remove(dispositivoWifiDestinatario);

        return DataMapper.parseObject(
                dispositivoWifiRepository.save(dispositivoWifiRemetente),
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