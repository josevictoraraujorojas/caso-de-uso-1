package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.MonitorEnergiaCreateDTO;
import org.example.casodeuso1.dto.MonitorEnergiaResponseDTO;
import org.example.casodeuso1.model.MonitorEnergia;
import org.example.casodeuso1.repository.MonitorEnergiaRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MonitorEnergiaService {

    private final MonitorEnergiaRepository monitorEnergiaRepository;

    public MonitorEnergiaService(MonitorEnergiaRepository monitorEnergiaRepository) {
        this.monitorEnergiaRepository = monitorEnergiaRepository;
    }

    public MonitorEnergiaResponseDTO salvar(
            MonitorEnergiaCreateDTO monitorEnergiaCreateDTO) {

        if (monitorEnergiaCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do monitor de energia inválidos"
            );
        }

        MonitorEnergia monitorEnergia = DataMapper.parseObject(
                monitorEnergiaCreateDTO,
                MonitorEnergia.class
        );

        return DataMapper.parseObject(
                monitorEnergiaRepository.save(monitorEnergia),
                MonitorEnergiaResponseDTO.class
        );
    }

    public MonitorEnergiaResponseDTO editar(
            Long monitorEnergiaId,
            MonitorEnergiaCreateDTO monitorEnergiaCreateDTO) {

        MonitorEnergia monitorEnergia = monitorEnergiaRepository.findById(monitorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Monitor de energia não encontrado"
                ));

        monitorEnergia.setNome(monitorEnergiaCreateDTO.getNome());
        monitorEnergia.setMacAddress(
                monitorEnergiaCreateDTO.getMacAddress()
        );
        monitorEnergia.setDataInstalacao(
                monitorEnergiaCreateDTO.getDataInstalacao()
        );

        return DataMapper.parseObject(
                monitorEnergiaRepository.save(monitorEnergia),
                MonitorEnergiaResponseDTO.class
        );
    }

    public MonitorEnergiaResponseDTO buscarPorId(Long monitorEnergiaId) {

        MonitorEnergia monitorEnergia = monitorEnergiaRepository.findById(monitorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Monitor de energia não encontrado"
                ));

        return DataMapper.parseObject(
                monitorEnergia,
                MonitorEnergiaResponseDTO.class
        );
    }

    public List<MonitorEnergiaResponseDTO> listar() {

        List<MonitorEnergia> monitores = monitorEnergiaRepository.findAll();

        if (monitores.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum monitor de energia encontrado"
            );
        }

        return DataMapper.parseListObjects(
                monitores,
                MonitorEnergiaResponseDTO.class
        );
    }

    public void excluir(Long monitorEnergiaId) {

        MonitorEnergia monitorEnergia = monitorEnergiaRepository.findById(monitorEnergiaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Monitor de energia não encontrado"
                ));

        monitorEnergiaRepository.delete(monitorEnergia);
    }
}