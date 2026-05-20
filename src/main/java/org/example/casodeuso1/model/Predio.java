package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Predio {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private String localizacao;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "TEM_LABORATORIO")
    private Set<Laboratorio> laboratorios;
    @Relationship(direction = Relationship.Direction.OUTGOING, type = "POSSUI_SNIFFER")
    private Set<Sniffer> sniffers;

    public Predio() {
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

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public Set<Laboratorio> getLaboratorios() {
        return laboratorios;
    }

    public void setLaboratorios(Set<Laboratorio> laboratorios) {
        this.laboratorios = laboratorios;
    }

    public Set<Sniffer> getSniffers() {
        return sniffers;
    }

    public void setSniffers(Set<Sniffer> sniffers) {
        this.sniffers = sniffers;
    }
}
