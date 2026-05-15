package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class Sniffer extends Esp32{
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "MONITORA_TRAFEGO_DISPOSITIVO")
    private Set<DispositivoWifi> dispositivosWifi;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "MONITORA_TRAFEGO_PONTOACESSO")
    private Set<PontoAcesso> pontosAcesso;


    public Sniffer() {
    }

    public Set<DispositivoWifi> getDispositivosWifi() {
        return dispositivosWifi;
    }

    public void setDispositivosWifi(Set<DispositivoWifi> dispositivosWifi) {
        this.dispositivosWifi = dispositivosWifi;
    }

    public Set<PontoAcesso> getPontosAcesso() {
        return pontosAcesso;
    }

    public void setPontosAcesso(Set<PontoAcesso> pontosAcesso) {
        this.pontosAcesso = pontosAcesso;
    }
}
