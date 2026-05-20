package org.example.casodeuso1.dto;

public class DispositivoWifiResumeDTO {
    private Long id;
    private String enderecoMac;

    public DispositivoWifiResumeDTO() {
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
}
