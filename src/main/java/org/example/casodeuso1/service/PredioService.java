package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.PredioCreateDTO;
import org.example.casodeuso1.dto.PredioResponseDTO;
import org.example.casodeuso1.model.Predio;
import org.example.casodeuso1.repository.PredioRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PredioService {

    private final PredioRepository predioRepository;

    public PredioService(PredioRepository predioRepository) {
        this.predioRepository = predioRepository;
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