package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.FiltroLinhaCreateDTO;
import org.example.casodeuso1.dto.FiltroLinhaResponseDTO;
import org.example.casodeuso1.model.Aparelho;
import org.example.casodeuso1.model.FiltroLinha;
import org.example.casodeuso1.model.MedidorEnergia;
import org.example.casodeuso1.repository.AparelhoRepository;
import org.example.casodeuso1.repository.FiltroLinhaRepository;
import org.example.casodeuso1.repository.MedidorEnergiaRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class FiltroLinhaService {

    private final FiltroLinhaRepository filtroLinhaRepository;
    private final AparelhoRepository aparelhoRepository;
    private final MedidorEnergiaRepository medidorEnergiaRepository;

    @Autowired
    public FiltroLinhaService(
            FiltroLinhaRepository filtroLinhaRepository,
            AparelhoRepository aparelhoRepository,
            MedidorEnergiaRepository medidorEnergiaRepository) {

        this.filtroLinhaRepository = filtroLinhaRepository;
        this.aparelhoRepository = aparelhoRepository;
        this.medidorEnergiaRepository = medidorEnergiaRepository;
    }

    public FiltroLinhaResponseDTO salvar(
            FiltroLinhaCreateDTO filtroLinhaCreateDTO) {

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

    public FiltroLinhaResponseDTO editar(
            Long filtroLinhaId,
            FiltroLinhaCreateDTO filtroLinhaCreateDTO) {

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        filtroLinha.setNome(filtroLinhaCreateDTO.getNome());
        filtroLinha.setCorrenteMaxima(
                filtroLinhaCreateDTO.getCorrenteMaxima()
        );
        filtroLinha.setCapacidade(
                filtroLinhaCreateDTO.getCapacidade()
        );

        return DataMapper.parseObject(
                filtroLinhaRepository.save(filtroLinha),
                FiltroLinhaResponseDTO.class
        );
    }

    public FiltroLinhaResponseDTO adicionarAparelho(
            Long filtroLinhaId,
            Long aparelhoId) {

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aparelho não encontrado"
                ));

        if (filtroLinha.getAparelhos() == null) {
            filtroLinha.setAparelhos(new HashSet<>());
        }

        if (filtroLinha.getAparelhos().contains(aparelho)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse aparelho já está vinculado ao filtro de linha"
            );
        }

        if (filtroLinha.getAparelhos().size() >= filtroLinha.getCapacidade()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Capacidade máxima do filtro de linha atingida"
            );
        }

        filtroLinha.getAparelhos().add(aparelho);

        return DataMapper.parseObject(
                filtroLinhaRepository.save(filtroLinha),
                FiltroLinhaResponseDTO.class
        );
    }



    public FiltroLinhaResponseDTO adicionarMedidorEnergia(
            Long filtroLinhaId,
            Long medidorEnergiaId) {

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        MedidorEnergia medidorEnergia = medidorEnergiaRepository
                .findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        if (filtroLinha.getMedidorEnergia() != null
                && filtroLinha.getMedidorEnergia().equals(medidorEnergia)) {

            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse medidor de energia já está vinculado ao filtro de linha"
            );
        }

        filtroLinha.setMedidorEnergia(medidorEnergia);

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

    public FiltroLinhaResponseDTO removerAparelho(
            Long filtroLinhaId,
            Long aparelhoId) {

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aparelho não encontrado"
                ));

        if (filtroLinha.getAparelhos() == null
                || filtroLinha.getAparelhos().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse filtro de linha não possui aparelhos vinculados"
            );
        }

        if (!filtroLinha.getAparelhos().contains(aparelho)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse aparelho não está vinculado ao filtro de linha"
            );
        }

        filtroLinha.getAparelhos().remove(aparelho);

        return DataMapper.parseObject(
                filtroLinhaRepository.save(filtroLinha),
                FiltroLinhaResponseDTO.class
        );
    }

    public FiltroLinhaResponseDTO removerMedidorEnergia(
            Long filtroLinhaId) {

        FiltroLinha filtroLinha = filtroLinhaRepository.findById(filtroLinhaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Filtro de linha não encontrado"
                ));

        if (filtroLinha.getMedidorEnergia() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse filtro de linha não possui medidor de energia vinculado"
            );
        }

        filtroLinha.setMedidorEnergia(null);

        return DataMapper.parseObject(
                filtroLinhaRepository.save(filtroLinha),
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