package pokedex.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static void escrever(String caminho, List<String> linhas) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
            for (String linha : linhas) {
                writer.write(linha);
                writer.newLine();
            }
        }
    }
    public static List<String> ler(String caminho) throws IOException {
        List<String> colunas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            reader.readLine();
            String linha;
            while ((linha = reader.readLine()) != null) {
                colunas.add(linha);
            }
        }
        return colunas;
    }
    public static void limpar(String caminho) throws IOException {
        try (FileWriter writer = new FileWriter(caminho, false)) {
            writer.write("");
        }
    }
}
