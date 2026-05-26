package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class MedidorEnergia {
    @Id
    @GeneratedValue
    private Long id;
    private String modelo;
    private int correnteNominal;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "CONECTADDO_A")
    private MonitorEnergia monitorEnergia;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "MEDE")
    private Set<Aparelho> aparelhos;
    @Relationship(direction = Relationship.Direction.INCOMING,type = "MONITORADO_POR")
    private FiltroLinha filtroLinha;

    public MedidorEnergia() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getCorrenteNominal() {
        return correnteNominal;
    }

    public void setCorrenteNominal(int correnteNominal) {
        this.correnteNominal = correnteNominal;
    }

    public MonitorEnergia getMonitorEnergia() {
        return monitorEnergia;
    }

    public void setMonitorEnergia(MonitorEnergia monitorEnergia) {
        this.monitorEnergia = monitorEnergia;
    }

    public Set<Aparelho> getAparelhos() {
        return aparelhos;
    }

    public void setAparelhos(Set<Aparelho> aparelhos) {
        this.aparelhos = aparelhos;
    }

    public FiltroLinha getFiltroLinha() {
        return filtroLinha;
    }

    public void setFiltroLinha(FiltroLinha filtroLinha) {
        this.filtroLinha = filtroLinha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MedidorEnergia that)) return false;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
