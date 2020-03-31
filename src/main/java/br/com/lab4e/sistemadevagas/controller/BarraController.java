package br.com.lab4e.sistemadevagas.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BarraController {
    @GetMapping(value = "/", produces = "text/plain")
    public String barra(){
        return "Hello World";
    }
}
