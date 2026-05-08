package pokedex.repository.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import pokedex.dataset.loader.MoveDatasetLoader;
import pokedex.domain.enums.Typing;
import pokedex.domain.model.Move;
import pokedex.repository.interfaces.ObjectRepository;
import pokedex.util.FileUtils;

public class FileMoveRepository implements ObjectRepository<Move> {
    private TreeMap<Integer, Move> moves;
    private MoveDatasetLoader loader; 
    private String filePath;
    public FileMoveRepository(String filePath) {
        moves = new TreeMap<>();
        loader = new MoveDatasetLoader();
        this.filePath = filePath;
    }
    @Override
    public boolean existe(String nome) {
        return moves
                .values()
                .stream()
                .anyMatch(m -> m.getName().equalsIgnoreCase(nome));
    }
    @Override
    public void salvar(Move m) {
        moves.put(m.getId(), m);
    }
    @Override
    public List<Move> listar() {
        return new ArrayList<>(moves.values());
    }
    @Override
    public Move buscarPorNome(String nome) {
        return moves.values()
                    .stream()
                    .filter(m -> m.getName().equalsIgnoreCase(nome))
                    .findFirst()
                    .orElse(null);
    }
    @Override
    public List<Move> buscarPorTipo(Typing tipo) {
        return moves.values()
                    .stream()
                    .filter(m -> m.getType() == tipo)
                    .collect(Collectors.toList());
    }
    @Override
    public void remover(int id) {
        moves.remove(id);
    }
    public void inserirMoves(List<Move> move) {
        for (Move m : move) {
            moves.put(m.getId(), m);
        }
    }
    public int contarMoves() {
        return moves.size();
    }
    public void escreverArquivo(List<Move> moves) throws IOException {
        List<String> linhas = new ArrayList<>();
        for (Move m : moves) {
            String linha = m.toFileString();
            linhas.add(linha);
        }
        FileUtils.escrever(filePath, linhas);
    }
    public List<Move> lerArquivo() {
        return loader.carregar(filePath);
    }
    public void limparArquivo() throws IOException {
        FileUtils.limpar(filePath);
    }
}
