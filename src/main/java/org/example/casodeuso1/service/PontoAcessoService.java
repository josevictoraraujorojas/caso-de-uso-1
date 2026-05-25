package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.PontoAcessoCreateDTO;
import org.example.casodeuso1.dto.PontoAcessoResponseDTO;
import org.example.casodeuso1.model.DispositivoWifi;
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
public class PontoAcessoService {

    private final PontoAcessoRepository pontoAcessoRepository;
    private final DispositivoWifiRepository dispositivoWifiRepository;

    @Autowired
    public PontoAcessoService(PontoAcessoRepository pontoAcessoRepository,
                              DispositivoWifiRepository dispositivoWifiRepository) {

        this.pontoAcessoRepository = pontoAcessoRepository;
        this.dispositivoWifiRepository = dispositivoWifiRepository;
    }

    public PontoAcessoResponseDTO salvar(
            PontoAcessoCreateDTO pontoAcessoCreateDTO) {

        if (pontoAcessoCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do ponto de acesso inválidos"
            );
        }

        PontoAcesso pontoAcesso = DataMapper.parseObject(
                pontoAcessoCreateDTO,
                PontoAcesso.class
        );

        return DataMapper.parseObject(
                pontoAcessoRepository.save(pontoAcesso),
                PontoAcessoResponseDTO.class
        );
    }

    public PontoAcessoResponseDTO editar(
            Long pontoAcessoId,
            PontoAcessoCreateDTO pontoAcessoCreateDTO) {

        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ponto de acesso não encontrado"
                ));

        pontoAcesso.setBssid(pontoAcessoCreateDTO.getBssid());

        return DataMapper.parseObject(
                pontoAcessoRepository.save(pontoAcesso),
                PontoAcessoResponseDTO.class
        );
    }

    public PontoAcessoResponseDTO adicionarDispositivoWifi(
            Long pontoAcessoId,
            Long dispositivoWifiId) {

        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ponto de acesso não encontrado"
                ));

        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoWifiId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dispositivo Wi-Fi não encontrado"
                ));

        if (pontoAcesso.getDispositivosWifiEnvia() == null) {
            pontoAcesso.setDispositivosWifiEnvia(new HashSet<>());
        }

        if (pontoAcesso.getDispositivosWifiEnvia().contains(dispositivoWifi)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse dispositivo Wi-Fi já está vinculado ao ponto de acesso"
            );
        }

        pontoAcesso.getDispositivosWifiEnvia().add(dispositivoWifi);

        return DataMapper.parseObject(
                pontoAcessoRepository.save(pontoAcesso),
                PontoAcessoResponseDTO.class
        );
    }

    public PontoAcessoResponseDTO removerDispositivoWifi(
            Long pontoAcessoId,
            Long dispositivoWifiId) {

        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ponto de acesso não encontrado"
                ));

        DispositivoWifi dispositivoWifi = dispositivoWifiRepository.findById(dispositivoWifiId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Dispositivo Wi-Fi não encontrado"
                ));

        if (pontoAcesso.getDispositivosWifiEnvia() == null ||
                pontoAcesso.getDispositivosWifiEnvia().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse ponto de acesso não possui dispositivos Wi-Fi vinculados"
            );
        }

        if (!pontoAcesso.getDispositivosWifiEnvia().contains(dispositivoWifi)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse dispositivo Wi-Fi não está vinculado ao ponto de acesso"
            );
        }

        pontoAcesso.getDispositivosWifiEnvia().remove(dispositivoWifi);

        return DataMapper.parseObject(
                pontoAcessoRepository.save(pontoAcesso),
                PontoAcessoResponseDTO.class
        );
    }

    public PontoAcessoResponseDTO buscarPorId(Long pontoAcessoId) {

        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ponto de acesso não encontrado"
                ));

        return DataMapper.parseObject(
                pontoAcesso,
                PontoAcessoResponseDTO.class
        );
    }

    public List<PontoAcessoResponseDTO> listar() {

        List<PontoAcesso> pontosAcesso = pontoAcessoRepository.findAll();

        if (pontosAcesso.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum ponto de acesso encontrado"
            );
        }

        return DataMapper.parseListObjects(
                pontosAcesso,
                PontoAcessoResponseDTO.class
        );
    }

    public void excluir(Long pontoAcessoId) {

        PontoAcesso pontoAcesso = pontoAcessoRepository.findById(pontoAcessoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Ponto de acesso não encontrado"
                ));

        pontoAcessoRepository.delete(pontoAcesso);
    }
}