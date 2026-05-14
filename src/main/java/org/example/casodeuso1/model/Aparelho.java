package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class Aparelho {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private int potenciaNominal;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private MedidorEnergia medidorEnergia;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private FiltroLinha filtroLinha;
}
