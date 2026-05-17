package pokedex.dataset.loader;

import java.util.ArrayList;
import java.util.List;

import pokedex.builder.PokemonBuilder;
import pokedex.dataset.parser.CsvParser;
import pokedex.domain.models.Pokemon;
import pokedex.dto.PokemonDTO;
import pokedex.exception.DadoInvalidoException;

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
            Integer id = Integer.parseInt(coluna[0]);
            String name = coluna[1];
            String types = coluna[2];
            String baseStats = coluna[3];
            PokemonDTO dto = new PokemonDTO(id, name, types, baseStats);
            try {
                Pokemon pokemon = new PokemonBuilder()
                                        .id(dto.id())
                                        .nome(dto.name())
                                        .tipos(parser.parseTypes(dto.types()))
                                        .statsBase(parser.parseStats(dto.stats()))
                                        .build();
                listaPokemons.add(pokemon);
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage() + " (Linha " + linhaNumero + " invalida!)");
            }
        }
        return listaPokemons;
    }
}
