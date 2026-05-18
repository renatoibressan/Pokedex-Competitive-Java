package pokedex.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pokedex.builder.TeamBuilder;
import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Team;
import pokedex.exception.DadoInvalidoException;
import pokedex.exception.TeamNaoEncontradoException;
import pokedex.repository.interfaces.ObjectRepository;

public class TeamService {
    private ObjectRepository<Team> repository;
    private List<Team> teams;
    public TeamService(ObjectRepository<Team> repository) {
        this.repository = repository;
        teams = new ArrayList<>();
    }
    public void putTeams(List<Team> teams) {
        this.teams = teams;
    }
    public int gerarNovoId() {
        return repository
                    .listar()
                    .stream()
                    .mapToInt(Team::getId)
                    .max()
                    .orElse(0) + 1;
    }
    public void criarTeam(int id, String name, List<Pokemon> pokemons) throws DadoInvalidoException {
        if (!repository.existe(name)) {
            Team t = new TeamBuilder()
                        .id(id)
                        .nome(name)
                        .pokemons(pokemons)
                        .build();
            repository.salvar(t);
            teams.add(t);
            return;
        }
        throw new DadoInvalidoException("Ja existe uma equipe com o nome " + name + "!");
    }
    public List<Team> listarTeams() {
        return repository
                .listar()
                .stream()
                .sorted(Comparator.comparingInt(Team::getId))
                .toList();
    }
    public Team buscarPorNome(String nome) throws TeamNaoEncontradoException {
        Team team = repository.buscarPorNome(nome);
        if (team == null) throw new TeamNaoEncontradoException("Equipe nao encontrada!");
        return team;
    }
    public void removerTeam(String name) throws TeamNaoEncontradoException {
        for (Team t : teams) {
            if (t.getName().equalsIgnoreCase(name)) {
                int id = t.getId();
                repository.remover(id);
                teams.remove(t);
                return;
            }
        }
        throw new TeamNaoEncontradoException("Equipe nao encontrada!");
    }
    public int contarListaTeams() {
        return teams.size();
    }
    public Team maiorBaseStatTotalMedio() throws TeamNaoEncontradoException {
        if (teams.isEmpty()) throw new TeamNaoEncontradoException("Lista de equipes vazia!");
        Team maior = teams.getFirst();
        for (Team t : teams) if (t.baseStatTotalMedio() > maior.baseStatTotalMedio()) maior = t;
        return maior;
    }
    public Team menorBaseStatTotalMedio() throws TeamNaoEncontradoException {
        if (teams.isEmpty()) throw new TeamNaoEncontradoException("Lista de equipes vazia!");
        Team menor = teams.getFirst();
        for (Team t : teams) if (t.baseStatTotalMedio() < menor.baseStatTotalMedio()) menor = t;
        return menor;
    }
    public Team maiorStatMedio(String optionStat) throws TeamNaoEncontradoException, DadoInvalidoException {
        if (teams.isEmpty()) throw new TeamNaoEncontradoException("Lista de equipes vazia!");
        Team maior = teams.getFirst();
        for (Team t : teams) if (t.statMedio(optionStat) > maior.statMedio(optionStat)) maior = t;
        return maior;
    }
    public Team menorStatMedio(String optionStat) throws TeamNaoEncontradoException, DadoInvalidoException {
        if (teams.isEmpty()) throw new TeamNaoEncontradoException("Lista de equipes vazia!");
        Team menor = teams.getFirst();
        for (Team t : teams) if (t.statMedio(optionStat) < menor.statMedio(optionStat)) menor = t;
        return menor;
    }
}
