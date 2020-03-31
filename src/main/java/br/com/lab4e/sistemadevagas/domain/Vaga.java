package br.com.lab4e.sistemadevagas.domain;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "vaga")
public class Vaga extends GenericDomain<Vaga>{
    @Column(name = "cargo", length = 127)
    private String cargo;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "locacao", length = 15)
    private String locacao;

    @Column(name = "tipo", length = 15)
    private String tipo;

    @Column(name = "data_de_vencimento")
    private Date dataDeVencimento;

    @Column(name = "situacao", length = 15)
    private String situacao;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocacao() {
        return locacao;
    }

    public void setLocacao(String locacao) {
        this.locacao = locacao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Date getDataDeVencimento() {
        return dataDeVencimento;
    }

    public void setDataDeVencimento(Date dataDeVencimento) {
        this.dataDeVencimento = dataDeVencimento;
    }

    public String getSituacao() {
        return situacao;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    @Override
    public void setEntidade(Vaga e) {
        this.cargo = e.getCargo();
        this.dataDeVencimento = e.getDataDeVencimento();
        this.descricao = e.getDescricao();
        this.locacao = e.getLocacao();
        this.situacao = e.getSituacao();
        this.tipo = e.getTipo();
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "cargo='" + cargo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", locacao='" + locacao + '\'' +
                ", tipo='" + tipo + '\'' +
                ", dataDeVencimento=" + dataDeVencimento +
                ", situacao='" + situacao + '\'' +
                '}';
    }
}
