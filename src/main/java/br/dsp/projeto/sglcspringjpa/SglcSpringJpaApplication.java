package br.dsp.projeto.sglcspringjpa;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.dsp.projeto.sglcspringjpa.dao.ElementoDAO;


@SpringBootApplication
public class SglcSpringJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(SglcSpringJpaApplication.class, args);


    }
}

