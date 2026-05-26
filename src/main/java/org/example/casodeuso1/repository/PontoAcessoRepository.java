package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.DispositivoWifi;
import org.example.casodeuso1.model.PontoAcesso;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PontoAcessoRepository extends Neo4jRepository<PontoAcesso, Long> {
    PontoAcesso findByBssid(String numero);
}
