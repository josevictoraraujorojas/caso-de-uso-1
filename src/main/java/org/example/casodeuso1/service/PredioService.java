package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.PredioCreateDTO;
import org.example.casodeuso1.dto.PredioResponseDTO;
import org.example.casodeuso1.model.Laboratorio;
import org.example.casodeuso1.model.Predio;
import org.example.casodeuso1.model.Sniffer;
import org.example.casodeuso1.repository.LaboratorioRepository;
import org.example.casodeuso1.repository.PredioRepository;
import org.example.casodeuso1.repository.SnifferRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class PredioService {

    private final PredioRepository predioRepository;
    private final LaboratorioRepository laboratorioRepository;
    private final SnifferRepository snifferRepository;

    public PredioService(
            PredioRepository predioRepository,
            LaboratorioRepository laboratorioRepository,
            SnifferRepository snifferRepository) {

        this.predioRepository = predioRepository;
        this.laboratorioRepository = laboratorioRepository;
        this.snifferRepository = snifferRepository;
    }

    public PredioResponseDTO salvar(
            PredioCreateDTO predioCreateDTO) {

        if (predioCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do prédio inválidos"
            );
        }

        Predio predio = DataMapper.parseObject(
                predioCreateDTO,
                Predio.class
        );

        return DataMapper.parseObject(
                predioRepository.save(predio),
                PredioResponseDTO.class
        );
    }

    public PredioResponseDTO editar(
            Long predioId,
            PredioCreateDTO predioCreateDTO) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        predio.setNome(predioCreateDTO.getNome());
        predio.setLocalizacao(predioCreateDTO.getLocalizacao());

        return DataMapper.parseObject(
                predioRepository.save(predio),
                PredioResponseDTO.class
        );
    }

    public PredioResponseDTO adicionarLaboratorio(
            Long predioId,
            Long laboratorioId) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        if (predio.getLaboratorios() == null) {
            predio.setLaboratorios(new HashSet<>());
        }

        if (predio.getLaboratorios().contains(laboratorio)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse laboratório já está vinculado ao prédio"
            );
        }

        predio.getLaboratorios().add(laboratorio);

        return DataMapper.parseObject(
                predioRepository.save(predio),
                PredioResponseDTO.class
        );
    }

    public PredioResponseDTO removerLaboratorio(
            Long predioId,
            Long laboratorioId) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        if (predio.getLaboratorios() == null ||
                predio.getLaboratorios().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse prédio não possui laboratórios"
            );
        }

        if (!predio.getLaboratorios().contains(laboratorio)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse laboratório não está vinculado ao prédio"
            );
        }

        predio.getLaboratorios().remove(laboratorio);

        return DataMapper.parseObject(
                predioRepository.save(predio),
                PredioResponseDTO.class
        );
    }

    public PredioResponseDTO adicionarSniffer(
            Long predioId,
            Long snifferId) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        if (predio.getSniffers() == null) {
            predio.setSniffers(new HashSet<>());
        }

        if (predio.getSniffers().contains(sniffer)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse sniffer já está vinculado ao prédio"
            );
        }

        predio.getSniffers().add(sniffer);

        return DataMapper.parseObject(
                predioRepository.save(predio),
                PredioResponseDTO.class
        );
    }

    public PredioResponseDTO removerSniffer(
            Long predioId,
            Long snifferId) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        Sniffer sniffer = snifferRepository.findById(snifferId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Sniffer não encontrado"
                ));

        if (predio.getSniffers() == null ||
                predio.getSniffers().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse prédio não possui sniffers"
            );
        }

        if (!predio.getSniffers().contains(sniffer)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse sniffer não está vinculado ao prédio"
            );
        }

        predio.getSniffers().remove(sniffer);

        return DataMapper.parseObject(
                predioRepository.save(predio),
                PredioResponseDTO.class
        );
    }

    public PredioResponseDTO buscarPorId(Long predioId) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        return DataMapper.parseObject(
                predio,
                PredioResponseDTO.class
        );
    }

    public List<PredioResponseDTO> listar() {

        List<Predio> predios = predioRepository.findAll();

        if (predios.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum prédio encontrado"
            );
        }

        return DataMapper.parseListObjects(
                predios,
                PredioResponseDTO.class
        );
    }

    public void excluir(Long predioId) {

        Predio predio = predioRepository.findById(predioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Prédio não encontrado"
                ));

        predioRepository.delete(predio);
    }
}