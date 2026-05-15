package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.MedidorEnergia;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedidorEnergiaRepository extends Neo4jRepository<MedidorEnergia, Long> {
}
