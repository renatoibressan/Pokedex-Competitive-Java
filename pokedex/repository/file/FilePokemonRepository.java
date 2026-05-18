package pokedex.repository.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import pokedex.dataset.loader.PokemonDatasetLoader;
import pokedex.domain.enums.Typing;
import pokedex.domain.models.Pokemon;
import pokedex.repository.interfaces.ObjectRepository;
import pokedex.util.FileUtils;

public class FilePokemonRepository implements ObjectRepository<Pokemon> {
    private TreeMap<Integer, Pokemon> pkmns;
    private PokemonDatasetLoader loader;
    private String filePath;
    public FilePokemonRepository(String filePath) {
        pkmns = new TreeMap<>();
        loader = new PokemonDatasetLoader();
        this.filePath = filePath;
    }
    @Override
    public boolean existe(String name) {
        return pkmns
                .values()
                .stream()
                .anyMatch(p -> p.getName().equalsIgnoreCase(name));
    }
    @Override
    public void salvar(Pokemon p) {
        pkmns.put(p.getId(), p);
    }
    @Override
    public List<Pokemon> listar() {
        return new ArrayList<>(pkmns.values());
    }
    @Override
    public Pokemon buscarPorNome(String name) {
        return pkmns.values()
                    .stream()
                    .filter(p -> p.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
    }
    @Override
    public List<Pokemon> buscarPorTipo(Typing type) {
        List<Pokemon> lista = pkmns.values()
                                    .stream()
                                    .filter(p -> p.getTypes().contains(type))
                                    .collect(Collectors.toList());
        return lista.isEmpty() ? ObjectRepository.super.buscarPorTipo(type) : lista;
    }
    @Override
    public void remover(int id) {
        pkmns.remove(id);
    }
    @Override
    public void inserirLista(List<Pokemon> pokemons) {
        for (Pokemon p : pokemons) {
            pkmns.put(p.getId(), p);
        }
    }
    @Override
    public void excluirTodos() {
        pkmns.clear();
    }
    @Override
    public int contarQuantidade() {
        return pkmns.size();
    }
    public void escreverArquivo(List<Pokemon> pokemons) throws IOException {
        List<String> linhas = new ArrayList<>();
        linhas.add("id,nome,tipos,hp|atq|def|atqEsp|defEsp|vel");
        for (Pokemon p : pokemons) {
            String linha = p.toFileString();
            linhas.add(linha);
        }
        FileUtils.escrever(filePath, linhas);
    }
    public List<Pokemon> lerArquivo() {
        return loader.carregar(filePath);
    }
    public void limparArquivo() throws IOException {
        FileUtils.limpar(filePath);
    }
}
