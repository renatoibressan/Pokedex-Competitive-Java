package pokedex.util;

import java.util.Scanner;

public class InputUtils {
    public static String lerString(String mensagem, Scanner sc) {
        System.out.print(mensagem);
        String txt = sc.nextLine();
        return txt;
    }
    public static int lerInt(String mensagem, Scanner sc) {
        int num;
        System.out.print(mensagem);
        while (!sc.hasNextInt()) {
            System.out.println("Entrada invalida!");
            sc.next();
            System.out.print(mensagem);
        }
        num = sc.nextInt();
        return num;
    }
}
