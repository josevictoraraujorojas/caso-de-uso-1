package org.example.casodeuso1.dto;

import java.util.Set;

public class DispositivoWifiResponseDTO {
    private Long id;
    private String enderecoMac;
    private Set<DispositivoWifiResumeDTO> dispositivosWifi;
    private Set<PontoAcessoResumeDTO> pontosAcessoEnvia;

    public DispositivoWifiResponseDTO() {
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

    public Set<DispositivoWifiResumeDTO> getDispositivosWifi() {
        return dispositivosWifi;
    }

    public void setDispositivosWifi(Set<DispositivoWifiResumeDTO> dispositivosWifi) {
        this.dispositivosWifi = dispositivosWifi;
    }

    public Set<PontoAcessoResumeDTO> getPontosAcessoEnvia() {
        return pontosAcessoEnvia;
    }

    public void setPontosAcessoEnvia(Set<PontoAcessoResumeDTO> pontosAcessoEnvia) {
        this.pontosAcessoEnvia = pontosAcessoEnvia;
    }
}
