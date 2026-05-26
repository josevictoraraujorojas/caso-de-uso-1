package org.example.casodeuso1.service;

import org.example.casodeuso1.model.EnergiaData;
import org.example.casodeuso1.repository.EnergiaDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EnergiaDataService {

    private final EnergiaDataRepository energiaDataRepository;

    @Autowired
    public EnergiaDataService(EnergiaDataRepository energiaDataRepository) {
        this.energiaDataRepository = energiaDataRepository;
    }

    public void salvar(EnergiaData energiaData) {

        if (energiaData == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Dados de energia inválidos"
            );
        }

        energiaDataRepository.salvar(energiaData);
    }

    public List<EnergiaData> listar() {

        List<EnergiaData> energias = energiaDataRepository.listar();

        if (energias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado de energia encontrado"
            );
        }

        return energias;
    }

    public List<EnergiaData> buscarPorAparelho(Long idAparelho) {

        List<EnergiaData> energias =
                energiaDataRepository.buscarPorAparelho(idAparelho);

        if (energias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o aparelho"
            );
        }

        return energias;
    }

    public List<EnergiaData> buscarPorEsp(Long idEsp) {

        List<EnergiaData> energias =
                energiaDataRepository.buscarPorEsp(idEsp);

        if (energias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o ESP"
            );
        }

        return energias;
    }

    public List<EnergiaData> buscarPorFiltro(Long idFiltro) {

        List<EnergiaData> energias =
                energiaDataRepository.buscarPorFiltro(idFiltro);

        if (energias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o filtro"
            );
        }

        return energias;
    }

    public List<EnergiaData> buscarPorLab(Long idLab) {

        List<EnergiaData> energias =
                energiaDataRepository.buscarPorLab(idLab);

        if (energias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o laboratório"
            );
        }

        return energias;
    }

    public List<EnergiaData> buscarPorPredio(Long idPredio) {

        List<EnergiaData> energias =
                energiaDataRepository.buscarPorPredio(idPredio);

        if (energias.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Nenhum dado encontrado para o prédio"
            );
        }

        return energias;
    }

    public void remover(Long idEsp) {

        energiaDataRepository.remover(idEsp);
    }
}
