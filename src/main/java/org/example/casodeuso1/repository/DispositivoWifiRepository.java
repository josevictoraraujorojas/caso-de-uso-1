package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.DispositivoWifi;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DispositivoWifiRepository extends Neo4jRepository<DispositivoWifi, Long> {

    DispositivoWifi findByEnderecoMac(String numero);

}
