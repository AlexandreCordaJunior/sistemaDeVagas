package br.com.lab4e.sistemadevagas.dao;

import br.com.lab4e.sistemadevagas.SistemadevagasApplication;
import br.com.lab4e.sistemadevagas.domain.GenericDomain;
import br.com.lab4e.sistemadevagas.domain.VagaColaborador;
import org.hibernate.Session;

import java.util.List;

// Classe generica para implementar as operações restfull de todas as entidades
// fica bem mais simples se implementar todas as dao though
public class GenericDao<Entidade extends GenericDomain> {
    private String className;

    public GenericDao(String className){
        this.className = className;
    }

    public void salvar(Entidade e) throws Exception {
        Session session = SistemadevagasApplication.factory.openSession();
        try {
            session.beginTransaction();

            session.save(e);

            session.getTransaction().commit();
        }
        catch (Exception ex){
            session.close();
            throw new Exception("Erro ao salvar: " + ex);
        }
        session.close();
    }

    public Entidade buscar(long codigo) throws Exception {
        Session session = SistemadevagasApplication.factory.openSession();
        Entidade entidade = null;
        try{
            session.beginTransaction();
            entidade = (Entidade) session.get(className, codigo);
            session.getTransaction().commit();
            session.close();
            return entidade;
        }
        catch (Exception e) {
            session.close();
            throw new Exception("Erro ao buscar: " + e);
        }
    }

    public List<Entidade> listar() throws Exception {
        Session session = SistemadevagasApplication.factory.openSession();
        List<Entidade> lista = null;
        try{
            session.beginTransaction();

            lista = (List<Entidade>) session.createQuery("from " + className).list();

            session.getTransaction().commit();
            session.close();
            return lista;
        }
        catch (Exception e){
            session.close();
            throw new Exception("Erro ao listar: " + e);
        }
    }

    public void apagar(long codigo) throws Exception {
        Session session = SistemadevagasApplication.factory.openSession();
        try{
            session.beginTransaction();

            Entidade entidade = (Entidade) session.get(className, codigo);
            if(entidade == null) {
                throw new Exception("Entidade não encontrada");
            }
            if(className.compareTo("br.com.lab4e.sistemadevagas.domain.Colaborador") == 0) {
                List<VagaColaborador> list = (
                        List<VagaColaborador>) session.createQuery(
                    "from vaga_colaborador as vc where vc.colaborador = " + entidade.getCodigo()
                ).list();

                for (VagaColaborador vg: list){
                    session.delete(vg);
                }

            }
            if(className.compareTo("br.com.lab4e.sistemadevagas.domain.Vaga") == 0) {
                List<VagaColaborador> list = (
                        List<VagaColaborador>) session.createQuery(
                    "from vaga_colaborador as vc where vc.vaga = " + entidade.getCodigo()
                ).list();

                for (VagaColaborador vg: list){
                    session.delete(vg);
                }
            }
            session.delete(entidade);

            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            session.close();
            throw new Exception("Erro ao deletar: " + e);
        }
    }

    public void alterar(Entidade novaEntidade, long codigo) throws Exception {
        Session session = SistemadevagasApplication.factory.openSession();
        try{
            session.beginTransaction();

            Entidade entidade = (Entidade) session.get(className, codigo);
            entidade.setEntidade(novaEntidade);
            session.update(entidade);

            session.getTransaction().commit();
            session.close();
        }
        catch (Exception e){
            session.close();
            throw new Exception("Erro ao alterar: " + e);
        }
    }
}
