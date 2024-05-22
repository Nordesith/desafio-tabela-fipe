package br.com.alura.desafioconsumoapis.TabelaFipe;

import br.com.alura.desafioconsumoapis.TabelaFipe.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumindoTabelaFipeApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(ConsumindoTabelaFipeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal();
        principal.exibeMenu();
    }
}