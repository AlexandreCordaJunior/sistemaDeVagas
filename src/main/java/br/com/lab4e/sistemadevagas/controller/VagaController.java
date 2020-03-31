package br.com.lab4e.sistemadevagas.controller;

import br.com.lab4e.sistemadevagas.dao.GenericDao;
import br.com.lab4e.sistemadevagas.domain.Vaga;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

// controller da classe vaga com as 5 operacoes restfull
@RestController
public class VagaController {

    @GetMapping(value = "/vaga", produces = "application/json")
    public ResponseEntity getVagas() {
        Gson gson = new Gson();
        GenericDao<Vaga> dao = new GenericDao<>(Vaga.class.getName());
        try{
            List<Vaga> list = dao.listar();
            if(list.size() == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
            return ResponseEntity.ok(gson.toJson(list));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping(value = "/vaga/{id}", produces = "application/json")
    public ResponseEntity getVaga(@PathVariable String id){
        Gson gson = new Gson();
        GenericDao<Vaga> dao = new GenericDao<>(Vaga.class.getName());
        try{
            Vaga vaga = dao.buscar(Integer.parseInt(id));
            if(vaga == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não encontrada");
            }
            return ResponseEntity.ok(gson.toJson(vaga));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/vaga", produces = "application/json")
    public ResponseEntity setVaga(@RequestBody Vaga vaga){
        if(vaga.getCargo() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'cargo' obrigatório");
        }
        if(vaga.getDataDeVencimento() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'dataDeVencimento' obrigatório");
        }
        if(vaga.getDescricao() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'descricao' obrigatório");
        }
        if(vaga.getLocacao() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'locacao' obrigatório");
        }
        if(vaga.getSituacao() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'situacao' obrigatório");
        }
        if(vaga.getTipo() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'tipo' obrigatório");
        }
        GenericDao<Vaga> dao = new GenericDao<>(Vaga.class.getName());
        try{
            dao.salvar(vaga);
            return ResponseEntity.ok("Salvo com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/vaga/{id}", produces = "application/json")
    public ResponseEntity deletarVaga(@PathVariable String id){
        GenericDao<Vaga> dao = new GenericDao<>(Vaga.class.getName());
        try{
            dao.apagar(Integer.parseInt(id));
            return ResponseEntity.status(200).body("Apagado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/vaga/{id}", produces = "application/json")
    public ResponseEntity alterarVaga(@RequestBody Vaga vaga,
                                      @PathVariable String id){
        if(vaga.getCargo() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'cargo' obrigatório");
        }
        if(vaga.getDataDeVencimento() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'dataDeVencimento' obrigatório");
        }
        if(vaga.getDescricao() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'descricao' obrigatório");
        }
        if(vaga.getLocacao() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'locacao' obrigatório");
        }
        if(vaga.getSituacao() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'situacao' obrigatório");
        }
        if(vaga.getTipo() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'tipo' obrigatório");
        }
        GenericDao<Vaga> dao = new GenericDao<>(Vaga.class.getName());
        try{
            dao.alterar(vaga, Integer.parseInt(id));
            return ResponseEntity.ok("Alterado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @CrossOrigin(allowedHeaders = "*", origins = "*")
    @GetMapping(value = "/vaga_disp", produces = "application/json")
    public ResponseEntity vagaDisp(){
        GenericDao<Vaga> dao = new GenericDao<>(Vaga.class.getName());
        List<Vaga> lista = null;

        try{
            lista = dao.listar();
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(e.getMessage());
        }

        lista = lista.stream().filter(l -> {
            return l.getTipo().compareToIgnoreCase("Interna") == 0 &&
                    l.getSituacao().compareToIgnoreCase("Aberta") == 0;
        }).collect(Collectors.toList());
        Gson gson = new Gson();
        return ResponseEntity.ok(gson.toJson(lista));
    }
}
