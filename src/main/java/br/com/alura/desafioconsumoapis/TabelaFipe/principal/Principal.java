package br.com.alura.desafioconsumoapis.TabelaFipe.principal;

import br.com.alura.desafioconsumoapis.TabelaFipe.model.Dados;
import br.com.alura.desafioconsumoapis.TabelaFipe.model.Modelos;
import br.com.alura.desafioconsumoapis.TabelaFipe.model.Veiculo;
import br.com.alura.desafioconsumoapis.TabelaFipe.service.ConsumoApi;
import br.com.alura.desafioconsumoapis.TabelaFipe.service.ConverteDados;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";
    private ConsumoApi consumo = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();

    public void exibeMenu() {
        var menu = """
                *** OPÇÕES ***
                Carro
                Moto
                Caminhão
                                
                Digite uma das opções para consultar:
                """;

        System.out.println(menu);
        var opcao = leitura.nextLine();
        String endreco;

        if (opcao.toLowerCase().contains("carr")) {
            endreco = URL_BASE + "carros/marcas";
        } else if (opcao.toLowerCase().contains("mot")) {
            endreco = URL_BASE + "motos/marcas";
        } else {
            endreco = URL_BASE + "caminhoes/marcas";
        }

        var json = consumo.obterDados(endreco);
        System.out.println(json);
        var marcas = conversor.obterLista(json, Dados.class);
        marcas.stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("Informe o código da marca para consulta: ");
        var codigoMarca = leitura.nextLine();

        endreco = endreco + "/" + codigoMarca + "/modelos";
        json = consumo.obterDados(endreco);
        var modeloLista = conversor.obterDados(json, Modelos.class);
        System.out.println("\nModelos dessa marca: ");
        modeloLista.modelos().stream()
                .sorted(Comparator.comparing(Dados::codigo))
                .forEach(System.out::println);

        System.out.println("\nDigite um techo do nome do carro a ser buscado: ");
        var nomeVeiculo = leitura.nextLine();

        List<Dados> modelosFiltrados = modeloLista.modelos().stream()
                .filter(m -> m.nome().toLowerCase().contains(nomeVeiculo.toLowerCase()))
                .collect(Collectors.toList());

        System.out.println("\nModelos filtrados: ");
        modelosFiltrados.forEach(System.out::println);

        System.out.println("\nDigite o código do modelo para buscar as avaliações: ");
        var codigoModelo = leitura.nextLine();

        endreco = endreco + "/" + codigoModelo + "/anos";
        json = consumo.obterDados(endreco);
        List<Dados> anos = conversor.obterLista(json, Dados.class);
        List<Veiculo> veiculos = new ArrayList<>();

        for (int i = 0; i < anos.size(); i++) {
            var enderecoAnos = endreco + "/" + anos.get(i).codigo();
            json = consumo.obterDados(enderecoAnos);
            Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
            veiculos.add(veiculo);
        }

        System.out.println("\nTodos os veiculos filtrados por ano: ");
        veiculos.forEach(System.out::println);

    }
}
