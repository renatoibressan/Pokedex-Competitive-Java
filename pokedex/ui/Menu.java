package pokedex.ui;

import pokedex.domain.enums.Typing;
import pokedex.domain.models.DamagingMove;
import pokedex.domain.models.Move;
import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Team;
import pokedex.util.OutputUtils;

public class Menu {
    public static void exibirMenuInicial() {
        System.out.println();
        System.out.println("| =================== POKEDEX MODULAR =================== |");
        System.out.println("| Desenvolvido por:                                       |");
        System.out.println("| Renato Ikeda Bressan - 202502254                        |");
        System.out.println("| Paulo Adriano Valotto - 202502245                       |");
        System.out.println("| ======================================================= |");
        System.out.println();
    }
    public static void exibirMenuPrincipal() {
        System.out.println();
        System.out.println("| ====================== MAIN MENU ====================== |");
        System.out.println("| 1. Cadastrar Pokemon                                    |");
        System.out.println("| 2. Listar Pokemons                                      |");
        System.out.println("| 3. Buscar Pokemon por nome                              |");
        System.out.println("| 4. Listar Pokemons por tipo                             |");
        System.out.println("| 5. Remover Pokemon                                      |");
        System.out.println("| 6. Registrar golpe                                      |");
        System.out.println("| 7. Listar golpes                                        |");
        System.out.println("| 8. Buscar golpe por nome                                |");
        System.out.println("| 9. Listar golpes por tipo                               |");
        System.out.println("| 10. Remover golpe                                       |");
        System.out.println("| 11. Criar equipe                                        |");
        System.out.println("| 12. Listar equipes                                      |");
        System.out.println("| 13. Buscar equipe por nome                              |");
        System.out.println("| 14. Adicionar a/remover da equipe                       |");
        System.out.println("| 15. Remover equipe                                      |");
        System.out.println("| 16. Simular batalha 1v1                                 |");
        System.out.println("| 17. Estatisticas de Pokemons                            |");
        System.out.println("| 18. Estatisticas de equipes                             |");
        System.out.println("| 19. Excluir os dados                                    |");
        System.out.println("| 20. Limpar os arquivos                                  |");
        System.out.println("| 0. Encerrar o programa                                  |");
        System.out.println("| ======================================================= |");
        System.out.println();
    }
    public static void exibirMenuPokemon(Pokemon pkmn, int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrintln("---------------------------------------------------------", delay);
        System.out.println("Nome: " + pkmn.getName());
        System.out.println("Numero de Pokedex: #" + String.format("%04d", pkmn.getId()));
        System.out.println("Tipo(s):");
        int i = 0;
        for (Typing t : pkmn.getTypes()) {
            if (i > 0) System.out.print("/");
            System.out.print(t);
            i++;
        }
        System.out.println("\nBase stats:");
        System.out.println("HP: " + pkmn.getBaseStats().getHp());
        System.out.println("Ataque: " + pkmn.getBaseStats().getAttack());
        System.out.println("Defesa: " + pkmn.getBaseStats().getDefense());
        System.out.println("Ataque especial: " + pkmn.getBaseStats().getSpecialAttack());
        System.out.println("Defesa especial: " + pkmn.getBaseStats().getSpecialDefense());
        System.out.println("Velocidade: " + pkmn.getBaseStats().getSpeed());
        System.out.println("BST: " + pkmn.getBST());
        OutputUtils.slowPrintln("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuGolpe(Move move, int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrintln("---------------------------------------------------------", delay);
        System.out.println("Nome: " + move.getName());
        System.out.println("Tipo: " + move.getType());
        System.out.println("Categoria: " + move.getCategory());
        if (move instanceof DamagingMove damagingMove) System.out.println("Dano base: " + damagingMove.getDamage());
        OutputUtils.slowPrintln("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuEquipe(Team team, int delay) throws InterruptedException {
        System.out.println();
        OutputUtils.slowPrintln("---------------------------------------------------------", delay);
        System.out.println("Nome: " + team.getName());
        System.out.println("Pokemons membros:");
        int i = 0;
        for (Pokemon pkmn : team.getPokemons()) {
            if (i > 0) System.out.print(" / ");
            System.out.print(pkmn.getName() + " (#" + String.format("%04d", pkmn.getId()) + ")");
            i++;
        }
        System.out.println("\nBST medio: " + team.baseStatTotalMedio());
        OutputUtils.slowPrintln("---------------------------------------------------------", delay);
        System.out.println();
    }
    public static void exibirMenuModificacaoEquipe() {
        System.out.println();
        System.out.println("| ======================================================= |");
        System.out.println("| A. Adicionar Pokemon                                    |");
        System.out.println("| B. Remover Pokemon                                      |");
        System.out.println("| 0. Encerrar operacao                                    |");
        System.out.println("| ======================================================= |");
        System.out.println();
    }
    public static void exibirMenuEstatisticasPokemons() {
        System.out.println();
        System.out.println("| ======================================================= |");
        System.out.println("| 1. Pokemon de maior stat                                |");
        System.out.println("| 2. Pokemon de menor stat                                |");
        System.out.println("| 0. Encerrar operacao                                    |");
        System.out.println("| ======================================================= |");
        System.out.println();
    }
    public static void exibirMenuEstatisticasEquipes() {
        System.out.println();
        System.out.println("| ======================================================= |");
        System.out.println("| 1. Equipe de maior BST medio                            |");
        System.out.println("| 2. Equipe de menor BST medio                            |");
        System.out.println("| 3. Equipe de maior stat medio                           |");
        System.out.println("| 4. Equipe de menor stat medio                           |");
        System.out.println("| 0. Encerrar operacao                                    |");
        System.out.println("| ======================================================= |");
        System.out.println();
    }
}
