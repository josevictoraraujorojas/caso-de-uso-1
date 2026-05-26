package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class FiltroLinha {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private int correnteMaxima;
    private int capacidade;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "ALIMENTA")
    private Set<Aparelho> aparelhos;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "MONITORADO_POR" )
    private MedidorEnergia medidorEnergia;
    @Relationship(direction = Relationship.Direction.INCOMING,type = "POSSUI_FILTRO")
    private Laboratorio laboratorio;

    public FiltroLinha() {
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

    public int getCorrenteMaxima() {
        return correnteMaxima;
    }

    public void setCorrenteMaxima(int correnteMaxima) {
        this.correnteMaxima = correnteMaxima;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public Set<Aparelho> getAparelhos() {
        return aparelhos;
    }

    public void setAparelhos(Set<Aparelho> aparelhos) {
        this.aparelhos = aparelhos;
    }

    public MedidorEnergia getMedidorEnergia() {
        return medidorEnergia;
    }

    public void setMedidorEnergia(MedidorEnergia medidorEnergia) {
        this.medidorEnergia = medidorEnergia;
    }

    public Laboratorio getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(Laboratorio laboratorio) {
        this.laboratorio = laboratorio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FiltroLinha that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
