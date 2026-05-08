package pokedex.dataset.loader;

import java.util.ArrayList;
import java.util.List;

import pokedex.builder.MoveBuilder;
import pokedex.dataset.parser.CsvParser;
import pokedex.domain.enums.MoveCategory;
import pokedex.domain.enums.Typing;
import pokedex.domain.model.Move;
import pokedex.exception.DadoInvalidoException;

public class MoveDatasetLoader {
    private final CsvParser parser;
    public MoveDatasetLoader() {
        this.parser = new CsvParser();
    }
    public List<Move> carregar(String filePath) {
        List<String[]> colunas = parser.parse(filePath);
        List<Move> listaMoves = new ArrayList<>();
        int linhaNumero = 0;
        for (String[] coluna : colunas) {
            linhaNumero++;
            try {
                Move move = new MoveBuilder()
                                    .id(Integer.parseInt(coluna[0]))
                                    .nome(coluna[1])
                                    .tipo(Typing.fromString(coluna[2]))
                                    .dano(Integer.parseInt(coluna[3]))
                                    .categoria(MoveCategory.fromString(coluna[4]))
                                    .build();
                listaMoves.add(move);
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage() + " (Linha " + linhaNumero + " invalida!)");
            }
        }
        return listaMoves;
    }
}
