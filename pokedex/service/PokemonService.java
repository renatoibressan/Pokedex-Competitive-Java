package pokedex.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pokedex.builder.PokemonBuilder;
import pokedex.domain.enums.Typing;
import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Stats;
import pokedex.exception.DadoInvalidoException;
import pokedex.exception.PokemonNaoEncontradoException;
import pokedex.repository.interfaces.ObjectRepository;

public class PokemonService {
    private ObjectRepository<Pokemon, Integer> repository;
    private List<Pokemon> pokemons;
    public PokemonService(ObjectRepository<Pokemon, Integer> repository) {
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
    public void cadastrarPokemon(int id, String name, List<Typing> types, Stats stats, int generation) throws DadoInvalidoException {
        if (!repository.existe(name)) {
            Pokemon p = new PokemonBuilder()
                            .id(id)
                            .nome(name)
                            .tipos(types)
                            .statsBase(stats)
                            .geracao(generation)
                            .build();
            repository.salvar(p);
            pokemons.add(p);
            return;
        }
        throw new DadoInvalidoException("Ja existe um Pokemon com o nome " + name + "!");
    }
    public List<Pokemon> listarPokemonsPorGeracao(int generation) {
        return repository
                .listarGrupo(generation)
                .stream()
                .sorted(Comparator.comparingInt(Pokemon::getId))
                .toList();
    }
    public Pokemon buscarPorNome(String name) throws PokemonNaoEncontradoException {
        Pokemon pkmn = repository.buscarPorNome(name);
        if (pkmn == null) throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
        return pkmn;
    }
    public List<Pokemon> buscarPorTipo(Typing type) {
        return repository.buscarPorTipo(type);
    }
    public void removerPokemon(String name) throws PokemonNaoEncontradoException {
        for (Pokemon p : pokemons) {
            if (p.getName().equalsIgnoreCase(name)) {
                int id = p.getId();
                repository.remover(id);
                pokemons.remove(p);
                return;
            }
        }
        throw new PokemonNaoEncontradoException("Pokemon nao encontrado!");
    }
    public void excluirTodosPokemons() throws PokemonNaoEncontradoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        repository.excluirTodos();
        pokemons.clear();
    }
    public int contarListaPokemons() {
        return repository.contarQuantidade();
    }
    public Pokemon maiorStat(String optionStat) throws PokemonNaoEncontradoException, DadoInvalidoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        Pokemon maior = pokemons.getFirst();
        for (Pokemon p : pokemons) if (p.getStatFromString(optionStat) > maior.getStatFromString(optionStat)) maior = p;
        return maior;
    }
    public Pokemon menorStat(String optionStat) throws PokemonNaoEncontradoException, DadoInvalidoException {
        if (pokemons.isEmpty()) throw new PokemonNaoEncontradoException("Lista de Pokemons vazia!");
        Pokemon menor = pokemons.getFirst();
        for (Pokemon p : pokemons) if (p.getStatFromString(optionStat) < menor.getStatFromString(optionStat)) menor = p;
        return menor;
    }
}
