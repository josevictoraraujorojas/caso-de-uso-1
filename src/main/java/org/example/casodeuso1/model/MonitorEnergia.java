package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

@Node
public class MonitorEnergia extends Esp32{
    @Relationship(direction = Relationship.Direction.INCOMING, type = "CONECTADDO_A")
    private MedidorEnergia medidorEnergia;

    public MonitorEnergia() {
    }

    public MedidorEnergia getMedidorEnergia() {
        return medidorEnergia;
    }

    public void setMedidorEnergia(MedidorEnergia medidorEnergia) {
        this.medidorEnergia = medidorEnergia;
    }
}
