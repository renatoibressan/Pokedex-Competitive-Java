package pokedex.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import pokedex.builder.MoveBuilder;
import pokedex.domain.enums.MoveCategory;
import pokedex.domain.enums.Typing;
import pokedex.domain.model.Move;
import pokedex.exception.DadoInvalidoException;
import pokedex.exception.MoveNaoEncontradoException;
import pokedex.repository.interfaces.ObjectRepository;

public class MoveService {
    private ObjectRepository<Move> repository;
    private List<Move> moves;
    public MoveService(ObjectRepository<Move> repository) {
        this.repository = repository;
        moves = new ArrayList<>();
    }
    public void putMoves(List<Move> moves) {
        this.moves = moves;
    }
    public int gerarNovoId() {
        return repository
                .listar()
                .stream()
                .mapToInt(Move::getId)
                .max()
                .orElse(0) + 1;
    }
    public void registrarMove(int id, String nome, Typing tipo, int dano, MoveCategory categoria) throws DadoInvalidoException {
        if (!repository.existe(nome)) {
            Move m = new MoveBuilder()
                        .id(id)
                        .nome(nome)
                        .tipo(tipo)
                        .dano(dano)
                        .categoria(categoria)
                        .build();
            repository.salvar(m);
            moves.add(m);
            return;
        }
        throw new DadoInvalidoException("Ja existe um golpe com o nome " + nome + "!");
    }
    public List<Move> listarMoves() {
        return repository
                .listar()
                .stream()
                .sorted(Comparator.comparingInt(Move::getId))
                .toList();
    }
    public Move buscarPorNome(String nome) throws MoveNaoEncontradoException {
        Move move = repository.buscarPorNome(nome);
        if (move == null) throw new MoveNaoEncontradoException("Golpe nao encontrado!");
        return move;
    }
    public List<Move> buscarPorTipo(Typing tipo) {
        return repository.buscarPorTipo(tipo);
    }
    public void removerMove(String nome) throws MoveNaoEncontradoException {
        for (Move m : moves) {
            if (m.getName().equalsIgnoreCase(nome)) {
                int id = m.getId();
                repository.remover(id);
                moves.remove(m);
                return;
            }
        }
        throw new MoveNaoEncontradoException("Golpe nao encontrado!");
    }
    public int contarListaMoves() {
        return moves.size();
    }
}
