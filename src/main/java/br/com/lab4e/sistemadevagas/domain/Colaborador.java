package br.com.lab4e.sistemadevagas.domain;

import javax.persistence.*;

@Entity(name = "colaborador")
public class Colaborador extends GenericDomain<Colaborador> {

    @Column(name = "nome", length = 127)
    private String nome;

    @Column(name = "cargo_atual", length = 127)
    private String cargoAtual;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCargoAtual() {
        return cargoAtual;
    }

    public void setCargoAtual(String cargoAtual) {
        this.cargoAtual = cargoAtual;
    }

    @Override
    public void setEntidade(Colaborador e) {
        this.cargoAtual = e.getCargoAtual();
        this.nome = e.getNome();
    }

    @Override
    public String toString() {
        return "Colaborador{" +
                "nome='" + nome + '\'' +
                ", cargoAtual='" + cargoAtual + '\'' +
                '}';
    }
}
