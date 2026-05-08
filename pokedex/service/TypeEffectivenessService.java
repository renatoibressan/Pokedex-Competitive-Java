package pokedex.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pokedex.domain.enums.Typing;
import pokedex.exception.DadoInvalidoException;
import pokedex.util.FileUtils;

public class TypeEffectivenessService {
    private String filePath;
    private Map<Typing, Map<Typing, Double>> effectiveness;
    public TypeEffectivenessService(String filePath) {
        this.filePath = filePath;
        effectiveness = new HashMap<>();
    }
    public double getMultiplicador(Typing attacker, Typing defender) {
        if (!effectiveness.containsKey(attacker)) return 1.0;
        Map<Typing, Double> mapaInterno = effectiveness.get(attacker);
        if (!mapaInterno.containsKey(defender)) return 1.0;
        double multiplicador = mapaInterno.get(defender);
        return multiplicador;
    }
    public void extrairDeArquivo() throws IOException {
        List<String> linhas = FileUtils.ler(filePath);
        Typing atacante, defensor;
        double multiplicador;
        int linhaNumero = 0;
        for (String linha : linhas) {
            linhaNumero++;
            try {
                String[] partes = linha.split(";");
                if (partes.length != 3) throw new IllegalArgumentException("Formato invalido!");
                atacante = Typing.fromString(partes[0]);
                defensor = Typing.fromString(partes[1]);
                multiplicador = Double.parseDouble(partes[2]);
                effectiveness
                    .computeIfAbsent(atacante, k -> new HashMap<>())
                    .put(defensor, multiplicador);
            } catch (DadoInvalidoException e) {
                System.out.println("Linha " + linhaNumero + " invalida!");
            }
        }
    }
}
