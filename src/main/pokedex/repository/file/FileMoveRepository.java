package src.main.pokedex.repository.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

import src.main.pokedex.dataset.loader.MoveDatasetLoader;
import src.main.pokedex.domain.enums.Typing;
import src.main.pokedex.domain.model.Move;
import src.main.pokedex.repository.interfaces.ObjectRepository;
import src.main.pokedex.util.FileUtils;

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
        for (Move m : moves.values()) {
            if (m.getName().equalsIgnoreCase(nome)) return true;
        }
        return false;
    }
    @Override
    public void salvar(Move m) {
        moves.put(m.getId(), m);
    }
    @Override
    public List<Move> listar() {
        List<Move> listaGolpes = new ArrayList<>(moves.values());
        return listaGolpes;
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
    public void inserirGolpes(List<Move> move) {
        for (Move m : move) {
            moves.put(m.getId(), m);
        }
    }
    public int contarGolpes() {
        return moves.size();
    }
    public void escreverArquivo(List<Move> moves) throws IOException {
        List<String> linhas = new ArrayList<>();
        String linha;
        for (Move m : moves) {
            linha = m.toFileString();
            linhas.add(linha);
        }
        FileUtils.escrever(filePath, linhas);
    }
    public List<Move> lerArquivo() {
        List<Move> listaMove = loader.carregar(filePath);
        return listaMove;
    }
    public void limparArquivo() throws IOException {
        FileUtils.limpar(filePath);
    }
}
