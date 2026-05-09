package pokedex.ui;

import pokedex.domain.enums.Typing;
import pokedex.domain.model.Move;
import pokedex.domain.model.Pokemon;
import pokedex.util.OutputUtils;

public class Menu {
    public static void exibirMenuInicial(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| =================== POKEDEX MODULAR =================== |", delay);
        OutputUtils.slowPrint("| Desenvolvido por:                                       |", delay);
        OutputUtils.slowPrint("| Renato Ikeda Bressan - 202502254                        |", delay);
        OutputUtils.slowPrint("| Paulo Adriano Valotto - 202502245                       |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
    public static void exibirMenuPrincipal(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| 1. Cadastrar Pokemon                                    |", delay);
        OutputUtils.slowPrint("| 2. Listar Pokemons                                      |", delay);
        OutputUtils.slowPrint("| 3. Buscar Pokemon por nome                              |", delay);
        OutputUtils.slowPrint("| 4. Listar Pokemons por tipo                             |", delay);
        OutputUtils.slowPrint("| 5. Editar Pokemon                                       |", delay);
        OutputUtils.slowPrint("| 6. Remover Pokemon                                      |", delay);
        OutputUtils.slowPrint("| 7. Registrar golpe                                      |", delay);
        OutputUtils.slowPrint("| 8. Listar golpes                                        |", delay);
        OutputUtils.slowPrint("| 9. Buscar golpe por nome                                |", delay);
        OutputUtils.slowPrint("| 10. Listar golpes por tipo                              |", delay);
        OutputUtils.slowPrint("| 11. Editar golpe                                        |", delay);
        OutputUtils.slowPrint("| 12. Remover golpe                                       |", delay);
        OutputUtils.slowPrint("| 13. Simular batalha                                     |", delay);
        OutputUtils.slowPrint("| 14. Estatisticas                                        |", delay);
        OutputUtils.slowPrint("| 15. Limpar os arquivos                                  |", delay);
        OutputUtils.slowPrint("| 0. Encerrar o programa                                  |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
    public static void exibirMenuPokemon(Pokemon pkmn, int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println("Nome: " + pkmn.getName());
        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
        System.out.print("Tipo(s):");
        for (Typing t : pkmn.getTypes()) System.out.print(" " + t);
        System.out.println("\nBase stats:");
        System.out.println("HP: " + pkmn.getBaseStats().getHp());
        System.out.println("Ataque: " + pkmn.getBaseStats().getAttack());
        System.out.println("Defesa: " + pkmn.getBaseStats().getDefense());
        System.out.println("Ataque especial: " + pkmn.getBaseStats().getSpecialAttack());
        System.out.println("Defesa especial: " + pkmn.getBaseStats().getSpecialDefense());
        System.out.println("Velocidade: " + pkmn.getBaseStats().getSpeed());
        System.out.println("BST: " + pkmn.getBST());
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuGolpe(Move move, int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println("Nome: " + move.getName());
        System.out.println("Tipo: " + move.getType());
        System.out.println("Dano base: " + move.getDamage());
        System.out.println("Categoria: " + move.getCategory());
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuEdicaoPokemon(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| 1. Editar nome                                          |", delay);
        OutputUtils.slowPrint("| 2. Editar tipo(s)                                       |", delay);
        OutputUtils.slowPrint("| 3. Editar stats                                         |", delay);
        OutputUtils.slowPrint("| 0. Encerrar operacao                                    |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
    public static void exibirMenuEdicaoGolpe(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| 1. Editar nome                                          |", delay);
        OutputUtils.slowPrint("| 2. Editar tipo                                          |", delay);
        OutputUtils.slowPrint("| 3. Editar dano base                                     |", delay);
        OutputUtils.slowPrint("| 4. Editar categoria                                     |", delay);
        OutputUtils.slowPrint("| 0. Encerrar operacao                                    |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
    public static void exibirMenuEstatisticas(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| 1. Pokemon de maior stat                                |", delay);
        OutputUtils.slowPrint("| 2. Pokemon de menor stat                                |", delay);
        OutputUtils.slowPrint("| 0. Encerrar operacao                                    |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
}
