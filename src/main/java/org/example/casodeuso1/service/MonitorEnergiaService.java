package org.example.casodeuso1.service;

import org.example.casodeuso1.dto.MonitorEnergiaCreateDTO;
import org.example.casodeuso1.dto.MonitorEnergiaResponseDTO;
import org.example.casodeuso1.model.MonitorEnergia;
import org.example.casodeuso1.repository.MonitorEnergiaRepository;
import org.example.casodeuso1.util.DataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MonitorEnergiaService {

    private final MonitorEnergiaRepository monitorEnergiaRepository;

    @Autowired
    public MonitorEnergiaService( MonitorEnergiaRepository monitorEnergiaRepository) {

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

    public MonitorEnergiaResponseDTO salvarOuAtualizar(
            MonitorEnergiaCreateDTO monitorEnergiaCreateDTO) {

        if (monitorEnergiaCreateDTO == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados do monitor de energia inválidos"
            );
        }

        MonitorEnergia monitorEnergiaExistente = monitorEnergiaRepository.findByMacAddress(monitorEnergiaCreateDTO.getMacAddress());

        if (monitorEnergiaExistente != null) {

            monitorEnergiaExistente.setNome(
                    monitorEnergiaCreateDTO.getNome()
            );

            monitorEnergiaExistente.setDataInstalacao(
                    monitorEnergiaCreateDTO.getDataInstalacao()
            );

            return DataMapper.parseObject(
                    monitorEnergiaRepository.save(monitorEnergiaExistente),
                    MonitorEnergiaResponseDTO.class
            );
        }

        MonitorEnergia novoMonitorEnergia = DataMapper.parseObject(
                monitorEnergiaCreateDTO,
                MonitorEnergia.class
        );

        return DataMapper.parseObject(
                monitorEnergiaRepository.save(novoMonitorEnergia),
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

    // BUSCAR POR MAC
    public MonitorEnergia buscarPorMac(String mac) {

        MonitorEnergia monitorEnergia = monitorEnergiaRepository.findByMacAddress(mac);

        if (monitorEnergia == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Monitor energia não encontrado"
            );
        }


        return monitorEnergia;
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