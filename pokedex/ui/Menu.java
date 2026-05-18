package pokedex.ui;

import pokedex.domain.enums.Typing;
import pokedex.domain.models.DamagingMove;
import pokedex.domain.models.Move;
import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Team;
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
        OutputUtils.slowPrint("| 5. Remover Pokemon                                      |", delay);
        OutputUtils.slowPrint("| 6. Registrar golpe                                      |", delay);
        OutputUtils.slowPrint("| 7. Listar golpes                                        |", delay);
        OutputUtils.slowPrint("| 8. Buscar golpe por nome                                |", delay);
        OutputUtils.slowPrint("| 9. Listar golpes por tipo                               |", delay);
        OutputUtils.slowPrint("| 10. Remover golpe                                       |", delay);
        OutputUtils.slowPrint("| 11. Criar equipe                                        |", delay);
        OutputUtils.slowPrint("| 12. Listar equipes                                      |", delay);
        OutputUtils.slowPrint("| 13. Buscar equipe por nome                              |", delay);
        OutputUtils.slowPrint("| 14. Adicionar a/remover da equipe                       |", delay);
        OutputUtils.slowPrint("| 15. Remover equipe                                      |", delay);
        OutputUtils.slowPrint("| 16. Simular batalha                                     |", delay);
        OutputUtils.slowPrint("| 17. Estatisticas de Pokemons                            |", delay);
        OutputUtils.slowPrint("| 18. Estatisticas de equipes                             |", delay);
        OutputUtils.slowPrint("| 19. Limpar os arquivos                                  |", delay);
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
        System.out.println("Categoria: " + move.getCategory());
        if (move instanceof DamagingMove damagingMove) System.out.println("Dano base: " + damagingMove.getDamage());
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuEquipe(Team team, int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println("Nome: " + team.getName());
        System.out.println("Pokemons membros:");
        int i = 0;
        for (Pokemon pkmn : team.getPokemons()) {
            if (i > 0) System.out.print("/");
            System.out.print(pkmn.getName() + " (#" + String.format("%04d", pkmn.getId()) + ")");
            i++;
        }
        System.out.println("\nBST medio: " + team.baseStatTotalMedio());
        OutputUtils.slowPrint("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuModificacaoEquipe(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| A. Adicionar Pokemon                                    |", delay);
        OutputUtils.slowPrint("| B. Remover Pokemon                                      |", delay);
        OutputUtils.slowPrint("| 0. Encerrar operacao                                    |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
    public static void exibirMenuEstatisticasPokemons(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| 1. Pokemon de maior stat                                |", delay);
        OutputUtils.slowPrint("| 2. Pokemon de menor stat                                |", delay);
        OutputUtils.slowPrint("| 0. Encerrar operacao                                    |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
    public static void exibirMenuEstatisticasEquipes(int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrint("| ======================================================= |", delay);
        OutputUtils.slowPrint("| 1. Equipe de maior BST medio                            |", delay);
        OutputUtils.slowPrint("| 2. Equipe de menor BST medio                            |", delay);
        OutputUtils.slowPrint("| 3. Equipe de maior stat medio                           |", delay);
        OutputUtils.slowPrint("| 4. Equipe de menor stat medio                           |", delay);
        OutputUtils.slowPrint("| 0. Encerrar operacao                                    |", delay);
        OutputUtils.slowPrint("| ======================================================= |", delay);
        System.out.println();
    }
}
