package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.EnergiaData;

import java.util.List;

public interface EnergiaDataRepository {
    void salvar(EnergiaData energiaData);

    List<EnergiaData> listar();

    List<EnergiaData> buscarPorAparelho(Long idAparelho);

    List<EnergiaData> buscarPorEsp(Long idEsp);

    List<EnergiaData> buscarPorFiltro(Long idFiltro);

    List<EnergiaData> buscarPorLab(Long idLab);

    List<EnergiaData> buscarPorPredio(Long idPredio);

    void remover(Long idEsp);
}
