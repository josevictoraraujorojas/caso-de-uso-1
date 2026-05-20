package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.LaboratorioCreateDTO;
import org.example.casodeuso1.dto.LaboratorioResponseDTO;
import org.example.casodeuso1.model.Laboratorio;
import org.example.casodeuso1.repository.LaboratorioRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LaboratorioService {

    private final LaboratorioRepository laboratorioRepository;

    @Autowired
    public LaboratorioService(LaboratorioRepository laboratorioRepository) {
        this.laboratorioRepository = laboratorioRepository;
    }

    public LaboratorioResponseDTO salvar(LaboratorioCreateDTO laboratorioCreateDTO) {
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

    public LaboratorioResponseDTO editar(Long laboratorioId,
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