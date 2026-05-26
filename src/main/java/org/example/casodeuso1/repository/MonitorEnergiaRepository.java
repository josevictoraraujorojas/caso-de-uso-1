package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.MonitorEnergia;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonitorEnergiaRepository extends Neo4jRepository<MonitorEnergia, Long> {

    MonitorEnergia findByMacAddress(String macAddress);

}
