package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class PontoAcesso {
    @Id
    @GeneratedValue
    private Long id;
    private String bssid;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "ENVIA_PARA_CLIENTE")
    private Set<DispositivoWifi> dispositivosWifiEnvia;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private Set<DispositivoWifi> dispositivossWifiRecebe;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private Set<Sniffer> sniffers;

    public PontoAcesso() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public Set<DispositivoWifi> getDispositivosWifiEnvia() {
        return dispositivosWifiEnvia;
    }

    public void setDispositivosWifiEnvia(Set<DispositivoWifi> dispositivosWifiEnvia) {
        this.dispositivosWifiEnvia = dispositivosWifiEnvia;
    }

    public Set<DispositivoWifi> getDispositivossWifiRecebe() {
        return dispositivossWifiRecebe;
    }

    public void setDispositivossWifiRecebe(Set<DispositivoWifi> dispositivossWifiRecebe) {
        this.dispositivossWifiRecebe = dispositivossWifiRecebe;
    }

    public Set<Sniffer> getSniffers() {
        return sniffers;
    }

    public void setSniffers(Set<Sniffer> sniffers) {
        this.sniffers = sniffers;
    }
}
