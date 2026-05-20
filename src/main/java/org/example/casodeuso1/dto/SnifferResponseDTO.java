package org.example.casodeuso1.dto;

import java.util.Set;

public class SnifferResponseDTO extends Esp32ResponseDTO{
    private Set<DispositivoWifiResumeDTO> dispositivosWifi;
    private Set<PontoAcessoResumeDTO> pontosAcesso;

    public SnifferResponseDTO() {
    }

    public Set<DispositivoWifiResumeDTO> getDispositivosWifi() {
        return dispositivosWifi;
    }

    public void setDispositivosWifi(Set<DispositivoWifiResumeDTO> dispositivosWifi) {
        this.dispositivosWifi = dispositivosWifi;
    }

    public Set<PontoAcessoResumeDTO> getPontosAcesso() {
        return pontosAcesso;
    }

    public void setPontosAcesso(Set<PontoAcessoResumeDTO> pontosAcesso) {
        this.pontosAcesso = pontosAcesso;
    }
}
