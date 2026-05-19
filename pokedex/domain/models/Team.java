package pokedex.domain.models;

import java.util.ArrayList;
import java.util.List;

import pokedex.exception.DadoInvalidoException;
import pokedex.serializer.TeamSerializer;

public class Team {
    private int id;
    private String name;
    private String trainer;
    private List<Pokemon> pokemons;
    public Team() {
        this.pokemons = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getTrainer() {
        return trainer;
    }
    public void setTrainer(String trainer) {
        this.trainer = trainer;
    }
    public List<Pokemon> getPokemons() {
        return pokemons;
    }
    public void setPokemons(List<Pokemon> pokemons) throws DadoInvalidoException {
        if (pokemons == null || pokemons.isEmpty()) throw new DadoInvalidoException("A equipe deve ter pelo menos 1 Pokemon!");
        if (pokemons.size() > 6) throw new DadoInvalidoException("A equipe nao pode ter mais de 6 Pokemons!");
        this.pokemons = pokemons;
    }
    public void addPokemon(Pokemon pokemon) throws DadoInvalidoException {
        if (pokemons.size() == 6) throw new DadoInvalidoException("A equipe nao pode ter mais de 6 Pokemons!");
        pokemons.add(pokemon);
    }
    public void removePokemon(String name) throws DadoInvalidoException {
        if (pokemons.size() == 1) throw new DadoInvalidoException("A equipe deve ter pelo menos 1 Pokemon!");
        for (Pokemon pkmn : pokemons) {
            if (pkmn.getName().equalsIgnoreCase(name)) {
                pokemons.remove(pkmn);
                return;
            }
        }
        throw new DadoInvalidoException("Pokemon " + name + " nao encontrado na equipe!");
    }
    public int baseStatTotalMedio() {
        int soma = 0;
        for (Pokemon p : pokemons) soma += p.getBST();
        return soma / pokemons.size();
    }
    public int statMedio(String optionStat) throws DadoInvalidoException {
        int soma = 0;
        for (Pokemon p : pokemons) soma += p.getStatFromString(optionStat);
        return soma / pokemons.size();
    }
    public String toFileString() {
        return TeamSerializer.serializeTeam(this);
    }
}
