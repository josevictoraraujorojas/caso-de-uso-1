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

    public Aparelho() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getPotenciaNominal() {
        return potenciaNominal;
    }

    public void setPotenciaNominal(int potenciaNominal) {
        this.potenciaNominal = potenciaNominal;
    }
}
