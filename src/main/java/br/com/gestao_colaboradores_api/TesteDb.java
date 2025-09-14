package br.com.gestao_colaboradores_api;

import jakarta.persistence.*;

@Entity
@Table(name = "testedb")
public class TesteDb {
    public String getTeste() {
        return teste;
    }

    public void setTeste(String teste) {
        this.teste = teste;
    }

    @Id
    private String teste;
}