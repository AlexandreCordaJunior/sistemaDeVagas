package br.com.lab4e.sistemadevagas.controller;

import br.com.lab4e.sistemadevagas.dao.GenericDao;
import br.com.lab4e.sistemadevagas.domain.Colaborador;
import br.com.lab4e.sistemadevagas.domain.Vaga;
import br.com.lab4e.sistemadevagas.domain.VagaColaborador;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// controller da classe vaga_colaborador com 4 operacoes restfull (sem alterar)
// usa uma classe helper no final para facilitar o uso da api
@RestController
public class VagaColaboradorController {

    @GetMapping(value = "/vaga_colaborador", produces = "application/json")
    public ResponseEntity getVagasColaborador(){
        Gson gson = new Gson();
        GenericDao<VagaColaborador> dao = new GenericDao<>(VagaColaborador.class.getName());
        try{
            List<VagaColaborador> list = dao.listar();
            if(list.size() == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
            return ResponseEntity.ok(gson.toJson(list));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/vaga_colaborador/{id}", produces = "application/json")
    public ResponseEntity getVagaColaborador(@PathVariable String id) {
        Gson gson = new Gson();
        GenericDao<VagaColaborador> dao = new GenericDao<>(VagaColaborador.class.getName());
        try{
            VagaColaborador vg = dao.buscar(Integer.parseInt(id));
            if(vg == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
            }
            return ResponseEntity.ok(gson.toJson(vg));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/vaga_colaborador", produces = "application/json")
    public ResponseEntity setVagaColaborador(@RequestBody Helper h){
        if(h.getColaborador() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'colaborador' obrigatório");
        }
        if(h.getVaga() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'vaga' obrigatório");
        }
        if(h.getTempo() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'tempo' obrigatório");
        }
        GenericDao<Colaborador> colaboradorDao = new GenericDao<>(Colaborador.class.getName());
        Colaborador colaborador = null;
        try{
            colaborador = colaboradorDao.buscar(h.getColaborador());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("'Colaborador' não encontrado");
        }

        if(colaborador == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("'Colaborador' não encontrado");
        }

        GenericDao<Vaga> vagaDao = new GenericDao<>(Vaga.class.getName());
        Vaga vaga = null;
        try{
            vaga = vagaDao.buscar(h.getVaga());
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("'Vaga' não encontrada");
        }

        if(vaga == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("'Vaga' não encontrada");
        }

        VagaColaborador vagaColaborador = new VagaColaborador();
        vagaColaborador.setColaborador(colaborador);
        vagaColaborador.setVaga(vaga);
        vagaColaborador.setDataDeCandidatura(h.getTempo());

        try{
            GenericDao<VagaColaborador> dao = new GenericDao<>(VagaColaborador.class.getName());
            dao.salvar(vagaColaborador);
            return ResponseEntity.ok("Salvo com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/vaga_colaborador/{id}", produces = "application/json")
    public ResponseEntity deletarVagaColaborador(@PathVariable String id){
        GenericDao<VagaColaborador> dao = new GenericDao<>(VagaColaborador.class.getName());
        try{
            dao.apagar(Integer.parseInt(id));
            return ResponseEntity.ok("Apagado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/vaga_colaborador/vaga/{id}", produces = "application/json")
    public ResponseEntity vagasColaboradorPorVagas(@PathVariable String id) {
        GenericDao<VagaColaborador> dao = new GenericDao<>(VagaColaborador.class.getName());
        Gson gson = new Gson();
        try{
            List<VagaColaborador> list = dao.listar();
            list = list.stream()
                    .filter(vagaColaborador -> {
                        return vagaColaborador.getVaga().getCodigo() == Integer.parseInt(id);
                    })
                    .collect(Collectors.toList());
            if(list.size() == 0){
                return ResponseEntity.status(404).body("Não há colaborador para esta vaga");
            }
            return ResponseEntity.ok(gson.toJson(list));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "/vaga_colaborador/colaborador/{id}", produces = "application/json")
    public ResponseEntity vagasColaboradorPorColaborador(@PathVariable String id) {
        GenericDao<VagaColaborador> dao = new GenericDao<>(VagaColaborador.class.getName());
        Gson gson = new Gson();
        try{
            List<VagaColaborador> list = dao.listar();
            list = list.stream()
                    .filter(vagaColaborador -> {
                        return vagaColaborador.getColaborador().getCodigo() == Integer.parseInt(id);
                    })
                    .collect(Collectors.toList());
            if(list.size() == 0){
                return ResponseEntity.status(404).body("Não há vagas para este colaborador");
            }
            return ResponseEntity.ok(gson.toJson(list));
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}
class Helper{
    public Integer colaborador;
    public Integer vaga;
    public Date tempo;

    public Integer getColaborador() {
        return colaborador;
    }

    public void setColaborador(Integer colaborador) {
        this.colaborador = colaborador;
    }

    public Integer getVaga() {
        return vaga;
    }

    public void setVaga(Integer vaga) {
        this.vaga = vaga;
    }

    public Date getTempo() {
        return tempo;
    }

    public void setTempo(Date tempo) {
        this.tempo = tempo;
    }
}
