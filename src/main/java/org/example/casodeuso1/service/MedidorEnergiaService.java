package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.MedidorEnergiaCreateDTO;
import org.example.casodeuso1.dto.MedidorEnergiaResponseDTO;
import org.example.casodeuso1.model.MedidorEnergia;
import org.example.casodeuso1.repository.MedidorEnergiaRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MedidorEnergiaService {

    private final MedidorEnergiaRepository medidorEnergiaRepository;

    @Autowired
    public MedidorEnergiaService(MedidorEnergiaRepository medidorEnergiaRepository) {
        this.medidorEnergiaRepository = medidorEnergiaRepository;
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