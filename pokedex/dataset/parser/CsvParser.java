package pokedex.dataset.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pokedex.builder.StatsBuilder;
import pokedex.domain.enums.Typing;
import pokedex.domain.models.Stats;
import pokedex.exception.DadoInvalidoException;

public class CsvParser {
    public List<String[]> parse(String filePath) {
        List<String[]> colunas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                colunas.add(linha.split(";"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return colunas;
    }
    public List<Typing> parseTypes(String types) {
        return Arrays
                .stream(types.split("\\|"))
                .map(String::trim)
                .map(Typing::valueOf)
                .toList();
    }
    public Stats parseStats(String stats) throws DadoInvalidoException {
        String[] statsString = stats.split("\\|");
        Stats baseStats = new StatsBuilder()
                                .hp(Integer.parseInt(statsString[0]))
                                .ataque(Integer.parseInt(statsString[1]))
                                .defesa(Integer.parseInt(statsString[2]))
                                .ataqueEspecial(Integer.parseInt(statsString[3]))
                                .defesaEspecial(Integer.parseInt(statsString[4]))
                                .velocidade(Integer.parseInt(statsString[5]))
                                .build();
        return baseStats;
    }
}
