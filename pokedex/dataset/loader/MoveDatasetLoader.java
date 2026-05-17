package pokedex.dataset.loader;

import java.util.ArrayList;
import java.util.List;

import pokedex.dataset.parser.CsvParser;
import pokedex.domain.models.Move;
import pokedex.dto.MoveDTO;
import pokedex.exception.DadoInvalidoException;
import pokedex.factory.MoveFactory;

public class MoveDatasetLoader {
    private final CsvParser parser;
    private final MoveFactory factory;
    public MoveDatasetLoader() {
        this.parser = new CsvParser();
        this.factory = new MoveFactory();
    }
    public List<Move> carregar(String filePath) {
        List<String[]> colunas = parser.parse(filePath);
        List<Move> listaMoves = new ArrayList<>();
        int linhaNumero = 0;
        for (String[] coluna : colunas) {
            linhaNumero++;
            Integer id = Integer.parseInt(coluna[0]);
            String name = coluna[1];
            String type = coluna[2];
            String category = coluna[3];
            boolean isStatus = category.equals("STATUS");
            Integer damage = isStatus ? null : Integer.parseInt(coluna[4]);
            String target = isStatus ? coluna[5] : null;
            String stat = isStatus ? coluna[6] : null;
            Integer modifier = isStatus ? Integer.parseInt(coluna[7]) : null;
            MoveDTO dto = new MoveDTO(id, name, type, category, damage, target, stat, modifier);
            try {
                Move move = factory.create(dto);
                listaMoves.add(move);
            } catch (DadoInvalidoException e) {
                System.out.println(e.getMessage() + " (Linha " + linhaNumero + " invalida!)");
            }
        }
        return listaMoves;
    } 
}
