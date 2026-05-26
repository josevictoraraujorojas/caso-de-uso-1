package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.MonitorEnergia;
import org.example.casodeuso1.model.Sniffer;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SnifferRepository extends Neo4jRepository<Sniffer, Long> {

    Sniffer findByMacAddress(String macAddress);

}
