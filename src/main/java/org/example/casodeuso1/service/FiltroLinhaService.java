package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.FiltroLinhaCreateDTO;
import org.example.casodeuso1.dto.FiltroLinhaResponseDTO;
import org.example.casodeuso1.model.FiltroLinha;
import org.example.casodeuso1.repository.FiltroLinhaRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FiltroLinhaService {

    private final FiltroLinhaRepository filtroLinhaRepository;

    @Autowired
    public FiltroLinhaService(FiltroLinhaRepository filtroLinhaRepository) {
        this.filtroLinhaRepository = filtroLinhaRepository;
    }

    public FiltroLinhaResponseDTO salvar(FiltroLinhaCreateDTO filtroLinhaCreateDTO) {
        if (filtroLinhaCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do filtro de linha inválidos"
            );
        }

        FiltroLinha filtroLinha = DataMapper.parseObject(
                filtroLinhaCreateDTO,
                FiltroLinha.class
        );

        return DataMapper.parseObject(
                filtroLinhaRepository.save(filtroLinha),
                FiltroLinhaResponseDTO.class
        );
    }

    public FiltroLinhaResponseDTO editar(Long filtroLinhaId,
                                         FiltroLinhaCreateDTO filtroLinhaCreateDTO) {

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        filtroLinha.setNome(filtroLinhaCreateDTO.getNome());
        filtroLinha.setCorrenteMaxima(filtroLinhaCreateDTO.getCorrenteMaxima());
        filtroLinha.setCapacidade(filtroLinhaCreateDTO.getCapacidade());

        return DataMapper.parseObject(
                filtroLinhaRepository.save(filtroLinha),
                FiltroLinhaResponseDTO.class
        );
    }

    public FiltroLinhaResponseDTO buscarPorId(Long filtroLinhaId) {
        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        return DataMapper.parseObject(
                filtroLinha,
                FiltroLinhaResponseDTO.class
        );
    }

    public List<FiltroLinhaResponseDTO> listar() {
        List<FiltroLinha> filtros = filtroLinhaRepository.findAll();

        if (filtros.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum filtro de linha encontrado"
            );
        }

        return DataMapper.parseListObjects(
                filtros,
                FiltroLinhaResponseDTO.class
        );
    }

    public void excluir(Long filtroLinhaId) {
        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        filtroLinhaRepository.delete(filtroLinha);
    }
}