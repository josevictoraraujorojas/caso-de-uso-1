package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.MedidorEnergiaCreateDTO;
import org.example.casodeuso1.dto.MedidorEnergiaResponseDTO;
import org.example.casodeuso1.model.Aparelho;
import org.example.casodeuso1.model.MedidorEnergia;
import org.example.casodeuso1.model.MonitorEnergia;
import org.example.casodeuso1.repository.AparelhoRepository;
import org.example.casodeuso1.repository.MedidorEnergiaRepository;
import org.example.casodeuso1.repository.MonitorEnergiaRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Service
public class MedidorEnergiaService {

    private final MedidorEnergiaRepository medidorEnergiaRepository;
    private final MonitorEnergiaRepository monitorEnergiaRepository;
    private final AparelhoRepository aparelhoRepository;

    @Autowired
    public MedidorEnergiaService(
            MedidorEnergiaRepository medidorEnergiaRepository,
            MonitorEnergiaRepository monitorEnergiaRepository,
            AparelhoRepository aparelhoRepository) {

        this.medidorEnergiaRepository = medidorEnergiaRepository;
        this.monitorEnergiaRepository = monitorEnergiaRepository;
        this.aparelhoRepository = aparelhoRepository;
    }

    public MedidorEnergiaResponseDTO salvar(
            MedidorEnergiaCreateDTO medidorEnergiaCreateDTO) {

        if (medidorEnergiaCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do medidor de energia inválidos"
            );
        }

        MedidorEnergia medidorEnergia = DataMapper.parseObject(
                medidorEnergiaCreateDTO,
                MedidorEnergia.class
        );

        return DataMapper.parseObject(
                medidorEnergiaRepository.save(medidorEnergia),
                MedidorEnergiaResponseDTO.class
        );
    }

    public MedidorEnergiaResponseDTO editar(
            Long medidorEnergiaId,
            MedidorEnergiaCreateDTO medidorEnergiaCreateDTO) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        medidorEnergia.setModelo(medidorEnergiaCreateDTO.getModelo());
        medidorEnergia.setCorrenteNominal(
                medidorEnergiaCreateDTO.getCorrenteNominal()
        );

        return DataMapper.parseObject(
                medidorEnergiaRepository.save(medidorEnergia),
                MedidorEnergiaResponseDTO.class
        );
    }

    public MedidorEnergiaResponseDTO adicionarMonitorEnergia(
            Long medidorEnergiaId,
            Long monitorEnergiaId) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        MonitorEnergia monitorEnergia = monitorEnergiaRepository.findById(monitorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Monitor de energia não encontrado"
                ));

        if (medidorEnergia.getMonitorEnergia() != null) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse medidor já possui um monitor de energia vinculado"
            );
        }

        medidorEnergia.setMonitorEnergia(monitorEnergia);

        return DataMapper.parseObject(
                medidorEnergiaRepository.save(medidorEnergia),
                MedidorEnergiaResponseDTO.class
        );
    }

    public MedidorEnergiaResponseDTO removerMonitorEnergia(
            Long medidorEnergiaId) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        if (medidorEnergia.getMonitorEnergia() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse medidor não possui monitor de energia vinculado"
            );
        }

        medidorEnergia.setMonitorEnergia(null);

        return DataMapper.parseObject(
                medidorEnergiaRepository.save(medidorEnergia),
                MedidorEnergiaResponseDTO.class
        );
    }

    public MedidorEnergiaResponseDTO adicionarAparelho(
            Long medidorEnergiaId,
            Long aparelhoId) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aparelho não encontrado"
                ));

        if (medidorEnergia.getAparelhos() == null) {
            medidorEnergia.setAparelhos(new HashSet<>());
        }

        if (medidorEnergia.getAparelhos().contains(aparelho)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse aparelho já está vinculado ao medidor"
            );
        }

        medidorEnergia.getAparelhos().add(aparelho);

        return DataMapper.parseObject(
                medidorEnergiaRepository.save(medidorEnergia),
                MedidorEnergiaResponseDTO.class
        );
    }

    public MedidorEnergiaResponseDTO removerAparelho(
            Long medidorEnergiaId,
            Long aparelhoId) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        Aparelho aparelho = aparelhoRepository.findById(aparelhoId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Aparelho não encontrado"
                ));

        if (medidorEnergia.getAparelhos() == null ||
                medidorEnergia.getAparelhos().isEmpty()) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Esse medidor não possui aparelhos vinculados"
            );
        }

        if (!medidorEnergia.getAparelhos().contains(aparelho)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Esse aparelho não está vinculado ao medidor"
            );
        }

        medidorEnergia.getAparelhos().remove(aparelho);

        return DataMapper.parseObject(
                medidorEnergiaRepository.save(medidorEnergia),
                MedidorEnergiaResponseDTO.class
        );
    }

    public MedidorEnergiaResponseDTO buscarPorId(Long medidorEnergiaId) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        return DataMapper.parseObject(
                medidorEnergia,
                MedidorEnergiaResponseDTO.class
        );
    }

    public List<MedidorEnergiaResponseDTO> listar() {

        List<MedidorEnergia> medidores = medidorEnergiaRepository.findAll();

        if (medidores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum medidor de energia encontrado"
            );
        }

        return DataMapper.parseListObjects(
                medidores,
                MedidorEnergiaResponseDTO.class
        );
    }

    public void excluir(Long medidorEnergiaId) {

        MedidorEnergia medidorEnergia = medidorEnergiaRepository.findById(medidorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Medidor de energia não encontrado"
                ));

        medidorEnergiaRepository.delete(medidorEnergia);
    }
}