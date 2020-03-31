package br.com.lab4e.sistemadevagas.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

// Classe usada como base para todas as entidades
// gera um id e declara o metodo setEntidade que será usado para alterar entidades
@MappedSuperclass
public abstract class GenericDomain <Entidade> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long codigo;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public abstract void setEntidade(Entidade e);
}
