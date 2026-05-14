package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Laboratorio {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "POSSUI_FILTRO")
    private Set<FiltroLinha> filtroLinhas;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private Predio predio;

    public Laboratorio() {
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

    public Set<FiltroLinha> getFiltroLinhas() {
        return filtroLinhas;
    }

    public void setFiltroLinhas(Set<FiltroLinha> filtroLinhas) {
        this.filtroLinhas = filtroLinhas;
    }

    public Predio getPredio() {
        return predio;
    }

    public void setPredio(Predio predio) {
        this.predio = predio;
    }
}
