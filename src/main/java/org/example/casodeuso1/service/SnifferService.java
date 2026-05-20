package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.SnifferCreateDTO;
import org.example.casodeuso1.dto.SnifferResponseDTO;
import org.example.casodeuso1.model.Sniffer;
import org.example.casodeuso1.repository.SnifferRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SnifferService {

    private final SnifferRepository snifferRepository;

    public SnifferService(SnifferRepository snifferRepository) {
        this.snifferRepository = snifferRepository;
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