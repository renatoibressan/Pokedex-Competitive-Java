package src.main.pokedex.builder;

import java.util.List;

import src.main.pokedex.domain.enums.Typing;
import src.main.pokedex.domain.model.Pokemon;
import src.main.pokedex.domain.model.Stats;
import src.main.pokedex.exception.DadoInvalidoException;

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
