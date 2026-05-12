package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.annotation.Nullable;
import java.util.Set;

@Node
public class PontoAcesso {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private String BSSID;
    private String SSID;
    @Relationship(type = "ENVIA_PARA_CLIENTE", direction = Relationship.Direction.OUTGOING)
    private Set<DispositivoWifi> clientes;

    public PontoAcesso() {
    }

    public Set<DispositivoWifi> getClientes() {
        return clientes;
    }

    public void setClientes(Set<DispositivoWifi> clientes) {
        this.clientes = clientes;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }
}
