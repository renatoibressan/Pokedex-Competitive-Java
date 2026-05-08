package pokedex.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pokedex.builder.PokemonBuilder;
import pokedex.domain.enums.Typing;
import pokedex.domain.model.Pokemon;
import pokedex.domain.model.Stats;
import pokedex.exception.DadoInvalidoException;
import pokedex.exception.PokemonNaoEncontradoException;
import pokedex.repository.interfaces.ObjectRepository;

public class PokemonService {
    private ObjectRepository<Pokemon> repository;
    private List<Pokemon> pokemons;
    public PokemonService(ObjectRepository<Pokemon> repository) {
        this.repository = repository;
        pokemons = new ArrayList<>();
    }
    public void putPokemons(List<Pokemon> pokemons) {
        this.pokemons = pokemons;
    }
    public int gerarNovoId() {
        return repository
                .listar()
                .stream()
                .mapToInt(Pokemon::getId)
                .max()
                .orElse(0) + 1;
    }
    public void cadastrarPokemon(int id, String nome, List<Typing> tipos, Stats stats) throws DadoInvalidoException {
        if (!repository.existe(nome)) {
            Pokemon p = new PokemonBuilder()
                            .id(id)
                            .nome(nome)
                            .tipos(tipos)
                            .statsBase(stats)
                            .build();
            repository.salvar(p);
            pokemons.add(p);
            return;
        }
        throw new DadoInvalidoException("Ja existe um Pokemon com o nome " + nome + "!");
    }
    public List<Pokemon> listarPokemons() {
        return repository
                .listar()
                .stream()
                .sorted(Comparator.comparingInt(Pokemon::getId))
                .toList();
    }
    public Pokemon buscarPorNome(String nome) throws PokemonNaoEncontradoException {
        Pokemon pkmn = repository.buscarPorNome(nome);
        if (pkmn == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        return pkmn;
    }
    public List<Pokemon> buscarPorTipo(Typing tipo) {
        return repository.buscarPorTipo(tipo);
    }
    public void removerPokemon(String nome) throws PokemonNaoEncontradoException {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(nome)) {
                int id = p.getId();
                repository.remover(id);
                pokemons.remove(p);
                return;
            }
        }
        throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
    }
    public int contarListaPokemons() {
        return pokemons.size();
    }
    public Pokemon maiorStat(String optionStat) throws PokemonNaoEncontradoException, DadoInvalidoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        Pokemon maior = pokemons.getFirst();
        for (Pokemon p : pokemons) if (p.statFromString(optionStat) > maior.statFromString(optionStat)) maior = p;
        return maior;
    }
    public Pokemon menorStat(String optionStat) throws PokemonNaoEncontradoException, DadoInvalidoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        Pokemon menor = pokemons.getFirst();
        for (Pokemon p : pokemons) if (p.statFromString(optionStat) < menor.statFromString(optionStat)) menor = p;
        return menor;
    }
}
