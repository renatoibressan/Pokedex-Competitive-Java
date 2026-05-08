package src.main.pokedex.app;

import java.util.Scanner;

import src.main.pokedex.ui.Menu;
import src.main.pokedex.util.InputUtils;
import src.main.pokedex.util.OutputUtils;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        OutputUtils.slowPrint("=============== POKEDEX MODULAR ===============", 40);
        OutputUtils.slowPrint("Desenvolvido por: Renato Ikeda Bressan", 40);
        Scanner sc = new Scanner(System.in);
        int option = -1;
        while (option != 0) {
            Menu.exibirMenuPrincipal(10);
            option = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            switch (option) {
                case 1:
                    // Cadastrar Pokemon
                    break;
                case 2:
                    // Listar Pokemons
                    break;
                case 3:
                    // Buscar Pokemon por nome
                    break;
                case 4:
                    // Listar Pokemons por tipo
                    break;
                case 5:
                    // Editar Pokemon
                    break;
                case 6:
                    // Remover Pokemon
                    break;
                case 7:
                    // Registrar golpe
                    break;
                case 8:
                    // Listar golpes
                    break;
                case 9:
                    // Buscar golpe por nome
                    break;
                case 10:
                    // Listar golpes por tipo
                    break;
                case 11:
                    // Editar golpe
                    break;
                case 12:
                    // Remover golpe
                    break;
                case 13:
                    // Simular batalha
                    break;
                case 14:
                    // Estatísticas
                    break;
                case 15:
                    // Limpar os arquivos
                    break;
                case 0:
                    System.out.print("Retornando ao inicio");
                    Thread.sleep(500);
                    OutputUtils.slowPrint("...", 150);
                    break;
                default:
                    System.out.println("Opcao invalida!");
                    sc.nextLine();
                    System.out.print("Retornando ao menu do programa principal");
                    Thread.sleep(500);
                    OutputUtils.slowPrint("...", 150);
            }
        }
        System.out.print("Encerrando o programa");
        Thread.sleep(500);
        OutputUtils.slowPrint("...", 150);
        sc.close();
    }
}
