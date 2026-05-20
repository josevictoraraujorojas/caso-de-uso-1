package org.example.casodeuso1.dto;

public class PredioCreateDTO {
    private String nome;
    private String localizacao;

    public PredioCreateDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }
}
