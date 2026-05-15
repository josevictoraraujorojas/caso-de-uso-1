package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.FiltroLinha;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FiltroLinhaRepository extends Neo4jRepository<FiltroLinha, Long> {

}
