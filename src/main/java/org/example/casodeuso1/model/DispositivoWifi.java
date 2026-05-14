package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
public class DispositivoWifi {
    @Id
    @GeneratedValue
    private Long id;
    private String enderecoMac;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "COMUNICA_DIRETO")
    private Set<DispositivoWifi> dispositivosWifi;
    @Relationship(direction = Relationship.Direction.OUTGOING,type = "ENVIA_PARA_PA")
    private Set<PontoAcesso> pontosAcessoEnvia;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private Set<Sniffer> sniffers;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private Set<DispositivoWifi> dispositivoWifiDireto;
    @Relationship(direction = Relationship.Direction.INCOMING)
    private Set<PontoAcesso> pontosAcessoRecebe;


    public DispositivoWifi() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnderecoMac() {
        return enderecoMac;
    }

    public void setEnderecoMac(String enderecoMac) {
        this.enderecoMac = enderecoMac;
    }

    public Set<DispositivoWifi> getDispositivosWifi() {
        return dispositivosWifi;
    }

    public void setDispositivosWifi(Set<DispositivoWifi> dispositivosWifi) {
        this.dispositivosWifi = dispositivosWifi;
    }

    public Set<PontoAcesso> getPontosAcessoEnvia() {
        return pontosAcessoEnvia;
    }

    public void setPontosAcessoEnvia(Set<PontoAcesso> pontosAcessoEnvia) {
        this.pontosAcessoEnvia = pontosAcessoEnvia;
    }

    public Set<PontoAcesso> getPontosAcessoRecebe() {
        return pontosAcessoRecebe;
    }

    public void setPontosAcessoRecebe(Set<PontoAcesso> pontosAcessoRecebe) {
        this.pontosAcessoRecebe = pontosAcessoRecebe;
    }

    public Set<Sniffer> getSniffers() {
        return sniffers;
    }

    public void setSniffers(Set<Sniffer> sniffers) {
        this.sniffers = sniffers;
    }

    public Set<DispositivoWifi> getDispositivoWifiDireto() {
        return dispositivoWifiDireto;
    }

    public void setDispositivoWifiDireto(Set<DispositivoWifi> dispositivoWifiDireto) {
        this.dispositivoWifiDireto = dispositivoWifiDireto;
    }
}
