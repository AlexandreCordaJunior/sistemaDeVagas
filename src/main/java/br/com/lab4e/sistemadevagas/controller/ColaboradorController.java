package br.com.lab4e.sistemadevagas.controller;

import br.com.lab4e.sistemadevagas.dao.GenericDao;
import br.com.lab4e.sistemadevagas.domain.Colaborador;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// controller da classe colaborador com as 5 operacoes restfull
@RestController
public class ColaboradorController {

    @GetMapping(value = "/colaborador", produces = "application/json")
    @ResponseBody
    public ResponseEntity getColaboradores() {
        Gson gson = new Gson();
        GenericDao<Colaborador> dao = new GenericDao<>(Colaborador.class.getName());
        try{
            List<Colaborador> list = dao.listar();
            if(list.size() == 0){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("");
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(list));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping(value = "/colaborador/{id}", produces = "application/json")
    public ResponseEntity getColaborador(@PathVariable String id) {
        Gson gson = new Gson();
        GenericDao<Colaborador> dao = new GenericDao<>(Colaborador.class.getName());
        try{
            Colaborador colaborador = dao.buscar(Integer.parseInt(id));
            if(colaborador == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Não encontrado");
            }
            return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(colaborador));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(value = "/colaborador", produces = "application/json")
    public ResponseEntity setColaborador(@RequestBody Colaborador colaborador){
        if(colaborador.getNome() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'nome' obrigatório");
        }

        if(colaborador.getCargoAtual() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Campo 'cargo atual' obrigatório");
        }

        GenericDao<Colaborador> dao = new GenericDao<>(Colaborador.class.getName());
        try{
            dao.salvar(colaborador);
            return ResponseEntity.status(HttpStatus.CREATED).body("Salvo com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/colaborador/{id}", produces = "application/json")
    public ResponseEntity deletarColaborador(@PathVariable String id){
        GenericDao<Colaborador> dao = new GenericDao<>(Colaborador.class.getName());
        try{
            dao.apagar(Integer.parseInt(id));
            return ResponseEntity.ok("Apagado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/colaborador/{id}", produces = "application/json")
    public ResponseEntity alterarColaborador(@RequestBody Colaborador colaborador,
                                             @PathVariable String id){
        if(colaborador.getNome() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome não pode ser nulo");
        }

        if(colaborador.getCargoAtual() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cargo Atual não pode ser nulo");
        }

        GenericDao<Colaborador> dao = new GenericDao<>(Colaborador.class.getName());
        try{
            dao.alterar(colaborador, Integer.parseInt(id));
            return ResponseEntity.status(HttpStatus.OK).body("Alterado com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
