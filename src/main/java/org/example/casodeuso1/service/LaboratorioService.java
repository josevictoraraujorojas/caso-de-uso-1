package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.LaboratorioCreateDTO;
import org.example.casodeuso1.dto.LaboratorioResponseDTO;
import org.example.casodeuso1.model.FiltroLinha;
import org.example.casodeuso1.model.Laboratorio;
import org.example.casodeuso1.repository.FiltroLinhaRepository;
import org.example.casodeuso1.repository.LaboratorioRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;
    private final FiltroLinhaRepository filtroLinhaRepository;

    @Autowired
    public LaboratorioService(
            LaboratorioRepository laboratorioRepository,
            FiltroLinhaRepository filtroLinhaRepository) {

        this.laboratorioRepository = laboratorioRepository;
        this.filtroLinhaRepository = filtroLinhaRepository;
    }

    public LaboratorioResponseDTO salvar(
            LaboratorioCreateDTO laboratorioCreateDTO) {

        if (laboratorioCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do laboratório inválidos"
            );
        }

        Laboratorio laboratorio = DataMapper.parseObject(
                laboratorioCreateDTO,
                Laboratorio.class
        );

        return DataMapper.parseObject(
                laboratorioRepository.save(laboratorio),
                LaboratorioResponseDTO.class
        );
    }

    public LaboratorioResponseDTO editar(
            Long laboratorioId,
            LaboratorioCreateDTO laboratorioCreateDTO) {

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        laboratorio.setNome(laboratorioCreateDTO.getNome());

        return DataMapper.parseObject(
                laboratorioRepository.save(laboratorio),
                LaboratorioResponseDTO.class
        );
    }

    public LaboratorioResponseDTO adicionarFiltroLinha(
            Long laboratorioId,
            Long filtroLinhaId) {

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        if (laboratorio.getFiltroLinhas() == null) {
            laboratorio.setFiltroLinhas(new HashSet<>());
        }

        if (laboratorio.getFiltroLinhas().contains(filtroLinha)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse filtro de linha já está vinculado ao laboratório"
            );
        }

        laboratorio.getFiltroLinhas().add(filtroLinha);

        return DataMapper.parseObject(
                laboratorioRepository.save(laboratorio),
                LaboratorioResponseDTO.class
        );
    }

    public LaboratorioResponseDTO removerFiltroLinha(
            Long laboratorioId,
            Long filtroLinhaId) {

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        if (laboratorio.getFiltroLinhas() == null ||
                laboratorio.getFiltroLinhas().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse laboratório não possui filtros de linha"
            );
        }

        if (!laboratorio.getFiltroLinhas().contains(filtroLinha)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse filtro de linha não está vinculado ao laboratório"
            );
        }

        laboratorio.getFiltroLinhas().remove(filtroLinha);

        return DataMapper.parseObject(
                laboratorioRepository.save(laboratorio),
                LaboratorioResponseDTO.class
        );
    }

    public LaboratorioResponseDTO buscarPorId(Long laboratorioId) {

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        return DataMapper.parseObject(
                laboratorio,
                LaboratorioResponseDTO.class
        );
    }

    public List<LaboratorioResponseDTO> listar() {

        List<Laboratorio> laboratorios = laboratorioRepository.findAll();

        if (laboratorios.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum laboratório encontrado"
            );
        }

        return DataMapper.parseListObjects(
                laboratorios,
                LaboratorioResponseDTO.class
        );
    }

    public void excluir(Long laboratorioId) {

        Laboratorio laboratorio = laboratorioRepository.findById(laboratorioId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Laboratório não encontrado"
                ));

        laboratorioRepository.delete(laboratorio);
    }
}