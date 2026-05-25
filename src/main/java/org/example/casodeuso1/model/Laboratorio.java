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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Laboratorio that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
