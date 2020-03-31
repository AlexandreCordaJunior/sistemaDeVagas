package br.com.lab4e.sistemadevagas.domain;

import javax.persistence.*;
import java.util.Date;


// Entidade entre vaga e colaborador para implementar o n para n
@Entity(name = "vaga_colaborador")
public class VagaColaborador extends GenericDomain<VagaColaborador> {
    @Column(name = "data_de_candidatura")
    private Date dataDeCandidatura;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "vaga_id")
    private Vaga vaga;

    @ManyToOne(cascade = {
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.PERSIST,
            CascadeType.REFRESH
    })
    @JoinColumn(name = "colaborador_id")
    private Colaborador colaborador;

    public Date getDataDeCandidatura() {
        return dataDeCandidatura;
    }

    public void setDataDeCandidatura(Date dataDeCandidatura) {
        this.dataDeCandidatura = dataDeCandidatura;
    }

    public Vaga getVaga() {
        return vaga;
    }

    public void setVaga(Vaga vaga) {
        this.vaga = vaga;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    @Override
    public void setEntidade(VagaColaborador e) {
        this.dataDeCandidatura = e.getDataDeCandidatura();
    }

    @Override
    public String toString() {
        return "VagaColaborador{" +
                "dataDeCandidatura=" + dataDeCandidatura +
                '}';
    }
}
