package pokedex.builder;

import java.util.List;

import pokedex.domain.enums.Typing;
import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Stats;
import pokedex.exception.DadoInvalidoException;

public class PokemonBuilder {
    private Pokemon pokemon;
    public PokemonBuilder() {
        pokemon = new Pokemon();
    }
    public PokemonBuilder id(int id) {
        pokemon.setId(id);
        return this;
    }
    public PokemonBuilder nome(String nome) {
        pokemon.setName(nome);
        return this;
    }
    public PokemonBuilder statsBase(Stats statsBase) {
        pokemon.setBaseStats(statsBase);
        return this;
    }
    public PokemonBuilder tipos(List<Typing> tipos) throws DadoInvalidoException {
        pokemon.setTypes(tipos);
        return this;
    }
    public Pokemon build() {
        return pokemon;
    }
}
