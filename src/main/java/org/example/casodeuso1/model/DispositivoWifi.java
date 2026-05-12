package org.example.casodeuso1.model;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.annotation.Nullable;
import java.util.Set;

@Node
public class DispositivoWifi {
    @Id
    @GeneratedValue
    private @Nullable Long id;
    private String mac;
    @Relationship(type = "COMUNICA_DIRETO", direction = Relationship.Direction.OUTGOING)
    private Set<DispositivoWifi> dispositivosDireto;
    @Relationship(type = "ENVIA_PARA_PA", direction = Relationship.Direction.OUTGOING)
    private Set<PontoAcesso> pontoAcessos;

    public DispositivoWifi() {
    }

    @Nullable
    public Long getId() {

        return id;
    }

    public void setId(@Nullable Long id) {
        this.id = id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public Set<DispositivoWifi> getDispositivosDireto() {
        return dispositivosDireto;
    }

    public void setDispositivosDireto(Set<DispositivoWifi> dispositivosDireto) {
        this.dispositivosDireto = dispositivosDireto;
    }

    public Set<PontoAcesso> getPontoAcessos() {
        return pontoAcessos;
    }

    public void setPontoAcessos(Set<PontoAcesso> pontoAcessos) {
        this.pontoAcessos = pontoAcessos;
    }
}
