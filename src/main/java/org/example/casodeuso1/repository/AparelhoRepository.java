package org.example.casodeuso1.repository;

import org.example.casodeuso1.model.Aparelho;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AparelhoRepository extends Neo4jRepository<Aparelho, Long> {
}
