package br.com.lab4e.sistemadevagas;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

// Onde a aplicação é iniciada
@SpringBootApplication
public class SistemadevagasApplication {

    public static SessionFactory factory;

    public static void main(String[] args) {
        SpringApplication.run(SistemadevagasApplication.class, args);
    }

    // metodo usado para criar apenas uma fabrica de sessoes do hibernate e não
    // sobrecarregar a api
    @PostConstruct
    public void init(){
        factory = new Configuration().configure().buildSessionFactory();
    }

}
