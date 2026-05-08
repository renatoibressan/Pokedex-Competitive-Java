package src.main.pokedex.dataset.loader;

import java.util.ArrayList;
import java.util.List;

import src.main.pokedex.builder.PokemonBuilder;
import src.main.pokedex.dataset.parser.CsvParser;
import src.main.pokedex.domain.model.Pokemon;
import src.main.pokedex.exception.DadoInvalidoException;

public class PokemonDatasetLoader {
    private final CsvParser parser;
    public PokemonDatasetLoader() {
        this.parser = new CsvParser();
    }
    public List<Pokemon> carregar(String filePath) {
        List<String[]> colunas = parser.parse(filePath);
        List<Pokemon> listaPokemons = new ArrayList<>();
        int linhaNumero = 0;
        for (String[] coluna : colunas) {
            linhaNumero++;
            try {
                Pokemon pokemon = new PokemonBuilder()
                                        .id(Integer.parseInt(coluna[0]))
                                        .nome(coluna[1])
                                        .tipos(parser.parseTypes(coluna[2]))
                                        .statsBase(parser.parseStats(coluna[3]))
                                        .build();
                listaPokemons.add(pokemon);
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage() + " (Linha " + linhaNumero + " invalida!)");
            }
        }
        return listaPokemons;
    }
}
