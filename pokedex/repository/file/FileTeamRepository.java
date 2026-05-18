package pokedex.repository.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import pokedex.dataset.loader.TeamDatasetLoader;
import pokedex.domain.models.Team;
import pokedex.repository.interfaces.ObjectRepository;
import pokedex.util.FileUtils;

public class FileTeamRepository implements ObjectRepository<Team> {
    private TreeMap<Integer, Team> teams;
    private TeamDatasetLoader loader;
    private String filePath;
    public FileTeamRepository(String filePath, TeamDatasetLoader loader) {
        teams = new TreeMap<>();
        this.loader = loader;
        this.filePath = filePath;
    }
    @Override
    public boolean existe(String name) {
        return teams
                .values()
                .stream()
                .anyMatch(t -> t.getName().equalsIgnoreCase(name));
    }
    @Override
    public void salvar(Team t) {
        teams.put(t.getId(), t);
    }
    @Override
    public List<Team> listar() {
        return new ArrayList<>(teams.values());
    }
    @Override
    public Team buscarPorNome(String name) {
        return teams.values()
                    .stream()
                    .filter(t -> t.getName().equalsIgnoreCase(name))
                    .findFirst()
                    .orElse(null);
    }
    @Override
    public void remover(int id) {
        teams.remove(id);
    }
    @Override
    public void inserirLista(List<Team> team) {
        for (Team t : team) {
            teams.put(t.getId(), t);
        }
    }
    @Override
    public int contarQuantidade() {
        return teams.size();
    }
    public void escreverArquivo(List<Team> teams) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Team t : teams) {
            String linha = t.toFileString();
            linhas.add(linha);
        }
        FileUtils.escrever(filePath, linhas);
    }
    public List<Team> lerArquivo() {
        return loader.carregar(filePath);
    }
    public void limparArquivo() throws IOException {
        FileUtils.limpar(filePath);
    }
}
