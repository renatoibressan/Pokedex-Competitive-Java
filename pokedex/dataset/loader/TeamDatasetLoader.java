package pokedex.dataset.loader;

import java.util.ArrayList;
import java.util.List;

import pokedex.builder.TeamBuilder;
import pokedex.dataset.parser.CsvParser;
import pokedex.domain.models.Team;
import pokedex.dto.TeamDTO;
import pokedex.exception.DadoInvalidoException;
import pokedex.repository.file.FilePokemonRepository;

public class TeamDatasetLoader {
    private final CsvParser parser;
    private final FilePokemonRepository repository;
    public TeamDatasetLoader(FilePokemonRepository repository) {
        this.parser = new CsvParser();
        this.repository = repository;
    }
    public List<Team> carregar(String filePath) {
        List<String[]> colunas = parser.parse(filePath);
        List<Team> listaTeams = new ArrayList<>();
        int linhaNumero = 0;
        for (String[] coluna : colunas) {
            linhaNumero++;
            Integer id = Integer.parseInt(coluna[0]);
            String name = coluna[1];
            String trainer = coluna[2];
            String pokemons = coluna[3];
            TeamDTO dto = new TeamDTO(id, name, trainer, pokemons);
            try {
                Team team = new TeamBuilder()
                                .id(dto.id())
                                .nome(dto.name())
                                .treinador(dto.trainer())
                                .pokemons(parser.parsePokemons(dto.pokemons(), repository))
                                .build();
                listaTeams.add(team);
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage() + " (Linha " + linhaNumero + " invalida!)");
            }
        }
        return listaTeams;
    }
}
