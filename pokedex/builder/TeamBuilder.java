package pokedex.builder;

import java.util.List;

import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Team;
import pokedex.exception.DadoInvalidoException;

public class TeamBuilder {
    private Team team;
    public TeamBuilder() {
        this.team = new Team();
    }
    public TeamBuilder id(int id) {
        team.setId(id);
        return this;
    }
    public TeamBuilder nome(String nome) {
        team.setName(nome);
        return this;
    }
    public TeamBuilder treinador(String treinador) {
        team.setTrainer(treinador);
        return this;
    }
    public TeamBuilder pokemons(List<Pokemon> pokemons) throws DadoInvalidoException {
        team.setPokemons(pokemons);
        return this;
    }
    public Team build() {
        return team;
    }
}
