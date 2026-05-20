package org.example.casodeuso1.dto;

import java.util.Set;

public class PontoAcessoResponseDTO {
    private Long id;
    private String bssid;
    private Set<DispositivoWifiResumeDTO> dispositivosWifiEnvia;

    public PontoAcessoResponseDTO() {
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

    public Set<DispositivoWifiResumeDTO> getDispositivosWifiEnvia() {
        return dispositivosWifiEnvia;
    }

    public void setDispositivosWifiEnvia(Set<DispositivoWifiResumeDTO> dispositivosWifiEnvia) {
        this.dispositivosWifiEnvia = dispositivosWifiEnvia;
    }
}
