package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.SnifferCreateDTO;
import org.example.casodeuso1.dto.SnifferResponseDTO;
import org.example.casodeuso1.model.DispositivoWifi;
import org.example.casodeuso1.model.PontoAcesso;
import org.example.casodeuso1.model.Sniffer;
import org.example.casodeuso1.repository.DispositivoWifiRepository;
import org.example.casodeuso1.repository.PontoAcessoRepository;
import org.example.casodeuso1.repository.SnifferRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class SnifferService {

    private final SnifferRepository snifferRepository;
    private final DispositivoWifiRepository dispositivoWifiRepository;
    private final PontoAcessoRepository pontoAcessoRepository;

    public SnifferService(
            SnifferRepository snifferRepository,
            DispositivoWifiRepository dispositivoWifiRepository,
            PontoAcessoRepository pontoAcessoRepository) {

        this.snifferRepository = snifferRepository;
        this.dispositivoWifiRepository = dispositivoWifiRepository;
        this.pontoAcessoRepository = pontoAcessoRepository;
    }

    public SnifferResponseDTO salvar(
            SnifferCreateDTO snifferCreateDTO) {

        if (snifferCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do sniffer inválidos"
            );
        }

        Sniffer sniffer = DataMapper.parseObject(
                snifferCreateDTO,
                Sniffer.class
        );

        return DataMapper.parseObject(
                snifferRepository.save(sniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO salvarOuAtualizar(
            SnifferCreateDTO snifferCreateDTO) {

        if (snifferCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do sniffer inválidos"
            );
        }

        Sniffer snifferExistente = snifferRepository.findByMacAddress(snifferCreateDTO.getMacAddress());

        if (snifferExistente != null) {

            snifferExistente.setNome(
                    snifferCreateDTO.getNome()
            );

            snifferExistente.setDataInstalacao(
                    snifferCreateDTO.getDataInstalacao()
            );

            return DataMapper.parseObject(
                    snifferRepository.save(snifferExistente),
                    SnifferResponseDTO.class
            );
        }

        Sniffer novoSniffer = DataMapper.parseObject(
                snifferCreateDTO,
                Sniffer.class
        );

        return DataMapper.parseObject(
                snifferRepository.save(novoSniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO editar(
            Long snifferId,
            SnifferCreateDTO snifferCreateDTO) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        sniffer.setNome(snifferCreateDTO.getNome());
        sniffer.setMacAddress(snifferCreateDTO.getMacAddress());
        sniffer.setDataInstalacao(
                snifferCreateDTO.getDataInstalacao()
        );

        return DataMapper.parseObject(
                snifferRepository.save(sniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO adicionarDispositivoWifi(
            Long snifferId,
            Long dispositivoWifiId) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        DispositivoWifi dispositivoWifi =
                dispositivoWifiRepository.findById(dispositivoWifiId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dispositivo Wi-Fi não encontrado"
                        ));

        if (sniffer.getDispositivosWifi() == null) {
            sniffer.setDispositivosWifi(new HashSet<>());
        }

        if (sniffer.getDispositivosWifi().contains(dispositivoWifi)) {
            return null;
        }

        sniffer.getDispositivosWifi().add(dispositivoWifi);

        return DataMapper.parseObject(
                snifferRepository.save(sniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO removerDispositivoWifi(
            Long snifferId,
            Long dispositivoWifiId) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        DispositivoWifi dispositivoWifi =
                dispositivoWifiRepository.findById(dispositivoWifiId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Dispositivo Wi-Fi não encontrado"
                        ));

        if (sniffer.getDispositivosWifi() == null ||
                sniffer.getDispositivosWifi().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse sniffer não possui dispositivos Wi-Fi"
            );
        }

        if (!sniffer.getDispositivosWifi().contains(dispositivoWifi)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse dispositivo Wi-Fi não está vinculado ao sniffer"
            );
        }

        sniffer.getDispositivosWifi().remove(dispositivoWifi);

        return DataMapper.parseObject(
                snifferRepository.save(sniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO adicionarPontoAcesso(
            Long snifferId,
            Long pontoAcessoId) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        PontoAcesso pontoAcesso =
                pontoAcessoRepository.findById(pontoAcessoId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ponto de acesso não encontrado"
                        ));

        if (sniffer.getPontosAcesso() == null) {
            sniffer.setPontosAcesso(new HashSet<>());
        }

        if (sniffer.getPontosAcesso().contains(pontoAcesso)) {
            return null;
        }

        sniffer.getPontosAcesso().add(pontoAcesso);

        return DataMapper.parseObject(
                snifferRepository.save(sniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO removerPontoAcesso(
            Long snifferId,
            Long pontoAcessoId) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        PontoAcesso pontoAcesso =
                pontoAcessoRepository.findById(pontoAcessoId)
                        .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Ponto de acesso não encontrado"
                        ));

        if (sniffer.getPontosAcesso() == null ||
                sniffer.getPontosAcesso().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse sniffer não possui pontos de acesso"
            );
        }

        if (!sniffer.getPontosAcesso().contains(pontoAcesso)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse ponto de acesso não está vinculado ao sniffer"
            );
        }

        sniffer.getPontosAcesso().remove(pontoAcesso);

        return DataMapper.parseObject(
                snifferRepository.save(sniffer),
                SnifferResponseDTO.class
        );
    }

    public SnifferResponseDTO buscarPorId(Long snifferId) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        return DataMapper.parseObject(
                sniffer,
                SnifferResponseDTO.class
        );
    }

    // BUSCAR POR MAC
    public Sniffer buscarPorMac(String mac) {

        Sniffer sniffer = snifferRepository.findByMacAddress(mac);

        if (sniffer == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "sniffer não encontrado"
            );
        }


        return sniffer;
    }

    public List<SnifferResponseDTO> listar() {

        List<Sniffer> sniffers = snifferRepository.findAll();

        if (sniffers.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum sniffer encontrado"
            );
        }

        return DataMapper.parseListObjects(
                sniffers,
                SnifferResponseDTO.class
        );
    }

    public void excluir(Long snifferId) {

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        snifferRepository.delete(sniffer);
    }
}