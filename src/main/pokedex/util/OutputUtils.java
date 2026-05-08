package src.main.pokedex.util;

public class OutputUtils {
    public static void slowPrint(String mensagem, int delay) throws InterruptedException {
        for (char c : mensagem.toCharArray()) {
            System.out.print(c);
            Thread.sleep(delay);
        }
        System.out.print("\n");
    }
}
