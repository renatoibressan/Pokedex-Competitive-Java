package pokedex.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import pokedex.builder.*;
import pokedex.dataset.loader.TeamDatasetLoader;
import pokedex.domain.enums.*;
import pokedex.domain.interfaces.*;
import pokedex.domain.models.*;
import pokedex.exception.*;
import pokedex.repository.file.*;
import pokedex.service.*;
import pokedex.ui.Menu;
import pokedex.util.InputUtils;
import pokedex.util.OutputUtils;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        // TFD: POO c/ Prof. Dirson, CC, INF-UFG, 2026.1
        Menu.exibirMenuInicial(15);
        Scanner sc = new Scanner(System.in);
        int optionMenu = -1;
        List<Pokemon> pokemons = new ArrayList<>();
        List<Move> moves = new ArrayList<>();
        List<Team> teams = new ArrayList<>();
        File arquivoPkmn = new File("pokedex/dataset/files/pokemons.csv");
        File arquivoMove = new File("pokedex/dataset/files/moves.csv");
        File arquivoTeam = new File("pokedex/dataset/files/teams.csv");
        FilePokemonRepository repoPkmn = new FilePokemonRepository("pokedex/dataset/files/pokemons.csv");
        FileMoveRepository repoMove = new FileMoveRepository("pokedex/dataset/files/moves.csv");
        FileTeamRepository repoTeam = new FileTeamRepository("pokedex/dataset/files/teams.csv", new TeamDatasetLoader(repoPkmn));
        PokemonService servPkmn = new PokemonService(repoPkmn);
        MoveService servMove = new MoveService(repoMove);
        TeamService servTeam = new TeamService(repoTeam);
        TypeEffectivenessService effect = new TypeEffectivenessService("pokedex/repository/data/matchups.txt");
        BattleService servBattle = new BattleService(effect);
        try {
            effect.extrairDeArquivo();
        } catch (IOException e) {
            System.out.println("Nao foi possivel carregar o arquivo!" + "(" + e.getMessage() + ")");
        }
        if (arquivoMove.exists() && arquivoMove.length() > 0) {
            moves = repoMove.lerArquivo();
            repoMove.inserirLista(moves);
            servMove.putMoves(moves);
            OutputUtils.slowPrint(repoMove.contarQuantidade() + " golpes foram carregados com sucesso!", 20);
        }
        if (arquivoPkmn.exists() && arquivoPkmn.length() > 0) {
            pokemons = repoPkmn.lerArquivo();
            repoPkmn.inserirLista(pokemons);
            servPkmn.putPokemons(pokemons);
            OutputUtils.slowPrint(repoPkmn.contarQuantidade() + " Pokemons foram carregados com sucesso!", 20);
            if (arquivoTeam.exists() && arquivoTeam.length() > 0) {
                teams = repoTeam.lerArquivo();
                repoTeam.inserirLista(teams);
                servTeam.putTeams(teams);
                OutputUtils.slowPrint(repoTeam.contarQuantidade() + " equipes foram carregadas com sucesso!", 20);
            }
        }
        do {
            Menu.exibirMenuPrincipal(5);
            optionMenu = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            switch (optionMenu) {
                case 1:
                    sc.nextLine();
                    String nome = InputUtils.lerString("Insira o nome do Pokemon: ", sc);
                    while (nome == null || nome.isEmpty()) {
                        nome = InputUtils.lerString("Entrada invalida!\nInsira o nome do Pokemon: ", sc);
                    }
                    nome = Pattern
                                .compile("\\b(\\w)")
                                .matcher(nome)
                                .replaceAll(m -> m.group(1).toUpperCase());
                    List<Typing> tipos = new ArrayList<>();
                    boolean tipoValido = false;
                    if (!pokemons.isEmpty()) {
                        Pokemon anterior = pokemons.getLast();
                        String txt1 = "Deseja utilizar o tipo " + anterior.getTypes().getFirst() + " do Pokemon " + anterior.getName() + "? (S/N): ";
                        String optionTipoAnterior1 = InputUtils.lerString(txt1, sc);
                        while (!optionTipoAnterior1.equalsIgnoreCase("s") && !optionTipoAnterior1.equalsIgnoreCase("n")) {
                            optionTipoAnterior1 = InputUtils.lerString("Opcao invalida!\n" + txt1, sc);
                        }
                        if (optionTipoAnterior1.equalsIgnoreCase("s")) {
                            Typing tipoAnterior1 = anterior.getTypes().getFirst();
                            tipos.add(tipoAnterior1);
                            tipoValido = true;
                        } else {
                            String tipo1 = InputUtils.lerString("Insira o tipo principal desejado: ", sc);
                            try {
                                Typing tipo1Pkmn = Typing.fromString(tipo1);
                                tipos.add(tipo1Pkmn);
                                tipoValido = true;
                            } catch (DadoInvalidoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        if (anterior.getTypes().size() == 2 && !tipos.contains(anterior.getTypes().getLast())) {
                            String txt2 = "Deseja utilizar o tipo " + anterior.getTypes().getLast() + " do Pokemon " + anterior.getName() + "? (S/N): ";
                            String optionTipoAnterior2 = InputUtils.lerString(txt2, sc);
                            while (!optionTipoAnterior2.equalsIgnoreCase("s") && !optionTipoAnterior2.equalsIgnoreCase("n")) {
                                optionTipoAnterior2 = InputUtils.lerString("Opcao invalida!\n" + txt2, sc);
                            }
                            if (optionTipoAnterior2.equalsIgnoreCase("s")) {
                                Typing tipoAnterior2 = anterior.getTypes().getLast();
                                tipos.add(tipoAnterior2);
                            } else {
                                String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                                    while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                                    optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                                }
                                if (optionTipoSec.equalsIgnoreCase("s")) {
                                    String tipo2 = InputUtils.lerString("Insira o tipo secundario desejado: ", sc);
                                    try {
                                        Typing tipo2Pkmn = Typing.fromString(tipo2);
                                        tipos.add(tipo2Pkmn);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                }
                            }
                        } else {
                            String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                            while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                                optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                            }
                            if (optionTipoSec.equalsIgnoreCase("s")) {
                                String tipo2 = InputUtils.lerString("Insira o tipo secundario desejado: ", sc);
                                try {
                                    Typing tipo2Pkmn = Typing.fromString(tipo2);
                                    tipos.add(tipo2Pkmn);
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                            }
                        }
                    } else {
                        String tipo1 = InputUtils.lerString("Insira o tipo principal desejado: ", sc);
                        String optionTipoSec = InputUtils.lerString("Deseja inserir um tipo secundario? (S/N): ", sc);
                        while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                            optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                        }
                        try {
                            Typing tipo1Pkmn = Typing.fromString(tipo1);
                            tipos.add(tipo1Pkmn);
                            tipoValido = true;
                            if (optionTipoSec.equalsIgnoreCase("s")) {
                                String tipo2 = InputUtils.lerString("Insira o tipo secundario desejado: ", sc);
                                Typing tipo2Pkmn = Typing.fromString(tipo2);
                                tipos.add(tipo2Pkmn);
                            }
                        } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel cadastrar o Pokemon: " + e.getMessage());
                        }
                    }
                    if (!tipoValido) break;
                    int hp = InputUtils.lerInt("Insira o HP base do Pokemon: ", sc);
                    int atk = InputUtils.lerInt("Insira o ataque base do Pokemon: ", sc);
                    int def = InputUtils.lerInt("Insira a defesa base do Pokemon: ", sc);
                    int spAtk = InputUtils.lerInt("Insira o ataque especial base do Pokemon: ", sc);
                    int spDef = InputUtils.lerInt("Insira a defesa especial base do Pokemon: ", sc);
                    int speed = InputUtils.lerInt("Insira a velocidade base do Pokemon: ", sc);
                    try {
                        Stats stats = new StatsBuilder()
                                            .hp(hp)
                                            .ataque(atk)
                                            .defesa(def)
                                            .ataqueEspecial(spAtk)
                                            .defesaEspecial(spDef)
                                            .velocidade(speed)
                                            .build();
                        int id = servPkmn.gerarNovoId();
                        Pokemon p = new PokemonBuilder()
                                        .id(id)
                                        .nome(nome)
                                        .tipos(tipos)
                                        .statsBase(stats)
                                        .build();
                        if (pokemons == null || pokemons.isEmpty()) pokemons.add(p);
                        servPkmn.cadastrarPokemon(id, nome, tipos, stats);
                        OutputUtils.slowPrint("Pokemon " + p.getName() + " cadastrado com sucesso!", 50);
                        System.out.println("Numero de Pokedex: #" + String.format("%04d", p.getId()));
                        System.out.print("Tipo(s):");
                        for (Typing t : p.getTypes()) {
                            System.out.print(" " + t);
                        }
                        System.out.println("\nBST: " + p.getBST());
                    } catch (DadoInvalidoException e) {
                        System.out.println("Nao foi possivel cadastrar o Pokemon: " + e.getMessage());
                    }
                    break;
                case 2:
                    List<Pokemon> listaPkmns = servPkmn.listarPokemons();
                     if (!listaPkmns.isEmpty()) {
                        OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        for (Pokemon pkmn : listaPkmns) {
                            System.out.println("Pokemon #" + String.format("%04d", pkmn.getId()) + ": " + pkmn.getName());
                            System.out.print("Tipo(s) de " + pkmn.getName() + ":");
                            for (Typing t : pkmn.getTypes()) System.out.print(" " + t);
                            OutputUtils.slowPrint("\n---------------------------------------------------------", 10);
                        }
                        OutputUtils.slowPrint(servPkmn.contarListaPokemons() + " Pokemons listados com sucesso!", 50);
                    } else System.out.println("Nao ha Pokemons para listar!");
                    break;
                case 3:
                    sc.nextLine();
                    String nomeBusca = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    nomeBusca = Pattern
                                    .compile("\\b(\\w)")
                                    .matcher(nomeBusca)
                                    .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Pokemon pkmn = servPkmn.buscarPorNome(nomeBusca);
                        Menu.exibirMenuPokemon(pkmn, 50);
                        OutputUtils.slowPrint("Pokemon " + nomeBusca + " encontrado com sucesso!", 50);
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    sc.nextLine();
                    String tipoBusca = InputUtils.lerString("Insira o tipo para listar os Pokemons: ", sc);
                    try {
                        Typing tipo = Typing.fromString(tipoBusca);
                        listaPkmns = servPkmn.buscarPorTipo(tipo);
                        if (!listaPkmns.isEmpty()) {
                            OutputUtils.slowPrint("---------------------------------------------------------", 10);
                            for (Pokemon pkmn : listaPkmns) {
                                System.out.println("Pokemon #" + String.format("%04d", pkmn.getId()) + ": " + pkmn.getName());
                                System.out.print("Tipo(s) de " + pkmn.getName() + ":");
                                for (Typing t : pkmn.getTypes()) System.out.print(" " + t);
                                OutputUtils.slowPrint("\n---------------------------------------------------------", 10);
                            }
                            OutputUtils.slowPrint(listaPkmns.size() + " Pokemons listados com sucesso!", 50);
                        } else System.out.println("Nao ha Pokemons de tipo " + tipo + " para listar!");
                    } catch (DadoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    sc.nextLine();
                    String nomeRemocao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    nomeRemocao = Pattern
                                    .compile("\\b(\\w)")
                                    .matcher(nomeRemocao)
                                    .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Pokemon pkmn = servPkmn.buscarPorNome(nomeRemocao);
                        String optionRemocao = InputUtils.lerString("Certeza que deseja remover " + pkmn.getName() + "? (S/N | Esta acao nao tem volta): ", sc);
                        while (!optionRemocao.equalsIgnoreCase("s") && !optionRemocao.equalsIgnoreCase("n")) {
                            optionRemocao = InputUtils.lerString("Opcao invalida!\nCerteza que deseja remover " + pkmn.getName() + "? (S/N | Esta acao nao tem volta): ", sc);
                        }
                        if (optionRemocao.equalsIgnoreCase("s")) {
                            servPkmn.removerPokemon(pkmn.getName());
                            OutputUtils.slowPrint("Pokemon " + nomeRemocao + " removido com sucesso!", 50);
                        }
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    sc.nextLine();
                    nome = InputUtils.lerString("Insira o nome do golpe: ", sc);
                    while (nome == null || nome.isEmpty()) {
                        nome = InputUtils.lerString("Entrada invalida!\nInsira o nome do golpe: ", sc);
                    }
                    nome = Pattern
                                .compile("\\b(\\w)")
                                .matcher(nome)
                                .replaceAll(m -> m.group(1).toUpperCase());
                    String tipoMove = InputUtils.lerString("Insira o tipo desejado para o golpe: ", sc);
                    String categoriaMove = InputUtils.lerString("Insira a categoria do golpe (physical/special/status): ", sc);
                    try {
                        Typing tipo = Typing.fromString(tipoMove);
                        MoveCategory categoria = MoveCategory.fromString(categoriaMove);
                        if (categoria instanceof DamagingMoveCategory) {
                            int dano = InputUtils.lerInt("Insira o dano base do golpe: ", sc);
                            int id = servMove.gerarNovoId();
                            DamagingMove m = new DamagingMoveBuilder()
                                                .id(id)
                                                .nome(nome)
                                                .tipo(tipo)
                                                .categoria(categoria)
                                                .dano(dano)
                                                .build();
                            if (moves == null || moves.isEmpty()) moves.add(m);
                            servMove.registrarDamagingMove(id, nome, tipo, categoria, dano);
                            OutputUtils.slowPrint("Golpe " + m.getName() + " registrado com sucesso!", 50);
                            System.out.println("Tipo: " + m.getType());
                            System.out.println("Categoria: " + m.getCategory());
                            System.out.println("Dano base: " + m.getDamage());
                        } else if (categoria instanceof StatusMoveCategory) {
                            String alvoMove = InputUtils.lerString("Insira o alvo do golpe (self/foe): ", sc);
                            Target alvo = Target.fromString(alvoMove);
                            String atributoMove = InputUtils.lerString("Insira o atributo modificado pelo golpe: ", sc);
                            StatType atributo = StatType.fromString(atributoMove);
                            int modificador = InputUtils.lerInt("Insira a quantidade de estagios de modificador do golpe: ", sc);
                            int id = servMove.gerarNovoId();
                            StatusMove m = new StatusMoveBuilder()
                                                .id(id)
                                                .nome(nome)
                                                .tipo(tipo)
                                                .categoria(categoria)
                                                .conjuntoEfeito(alvo, atributo, modificador)
                                                .build();
                            if (moves == null || moves.isEmpty()) moves.add(m);
                            servMove.registrarStatusMove(id, nome, tipo, categoria, alvo, atributo, modificador);
                            OutputUtils.slowPrint("Golpe " + m.getName() + " registrado com sucesso!", 50);
                            System.out.println("Tipo: " + m.getType());
                            System.out.println("Categoria: " + m.getCategory());
                        }
                    } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel registrar o golpe: " + e.getMessage());
                    }
                    break;
                case 7:
                    List<Move> listaMoves= servMove.listarMoves();
                    if (!listaMoves.isEmpty()) {
                        OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        for (Move move : listaMoves) {
                            System.out.println("Dados do golpe " + move.getName() + ":");
                            System.out.println("Tipo: " + move.getType());
                            System.out.println("Categoria: " + move.getCategory());
                            if (move instanceof DamagingMove damagingMove) System.out.println("Dano base: " + damagingMove.getDamage());
                            OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        }
                        OutputUtils.slowPrint(servMove.contarListaMoves() + " golpes listados com sucesso!", 50);
                    } else System.out.println("Nao ha golpes para listar!");
                    break;
                case 8:
                    sc.nextLine();
                    nomeBusca = InputUtils.lerString("Insira o nome do golpe para procura: ", sc);
                    nomeBusca = Pattern
                                    .compile("\\b(\\w)")
                                    .matcher(nomeBusca)
                                    .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Move move = servMove.buscarPorNome(nomeBusca);
                        Menu.exibirMenuGolpe(move, 50);
                        OutputUtils.slowPrint("Golpe " + nomeBusca + " encontrado com sucesso!", 50);
                    } catch (MoveNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 9:
                    sc.nextLine();
                    tipoBusca = InputUtils.lerString("Insira o tipo para listar os golpes: ", sc);
                    try {
                        Typing tipo = Typing.fromString(tipoBusca);
                        listaMoves = servMove.buscarPorTipo(tipo);
                        if (!listaMoves.isEmpty()) {
                            OutputUtils.slowPrint("---------------------------------------------------------", 10);
                            for (Move move : listaMoves) {
                                System.out.println("Dados do golpe " + move.getName() + ":");
                                System.out.println("Tipo: " + move.getType());
                                System.out.println("Categoria: " + move.getCategory());
                                if (move instanceof DamagingMove damagingMove) System.out.println("Dano base: " + damagingMove.getDamage());
                                OutputUtils.slowPrint("---------------------------------------------------------", 10);
                            }
                            OutputUtils.slowPrint(listaMoves.size() + " golpes listados com sucesso!", 50);
                        } else System.out.println("Nao ha golpes de tipo " + tipo + " para listar!");
                    } catch (DadoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 10:
                    sc.nextLine();
                    nomeRemocao = InputUtils.lerString("Insira o nome do golpe para procura: ", sc);
                    nomeRemocao = Pattern
                                    .compile("\\b(\\w)")
                                    .matcher(nomeRemocao)
                                    .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Move move = servMove.buscarPorNome(nomeRemocao);
                        String optionRemocao = InputUtils.lerString("Certeza que deseja remover " + move.getName() + "? (S/N | Esta acao nao tem volta): ", sc);
                        while (!optionRemocao.equalsIgnoreCase("s") && !optionRemocao.equalsIgnoreCase("n")) {
                            optionRemocao = InputUtils.lerString("Opcao invalida!\nCerteza que deseja remover " + move.getName() + "? (S/N | Esta acao nao tem volta): ", sc);
                        }
                        if (optionRemocao.equalsIgnoreCase("s")) {
                            servMove.removerMove(move.getName());
                            OutputUtils.slowPrint("Pokemon " + nomeRemocao + " removido com sucesso!", 50);
                        }
                    } catch (MoveNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 11:
                    sc.nextLine();
                    nome = InputUtils.lerString("Insira o nome da equipe: ", sc);
                    while (nome == null || nome.isEmpty()) {
                        nome = InputUtils.lerString("Entrada invalida!\nInsira o nome do golpe: ", sc);
                    }
                    nome = Pattern
                                .compile("\\b(\\w)")
                                .matcher(nome)
                                .replaceAll(m -> m.group(1).toUpperCase());
                    List<Pokemon> membros = new ArrayList<>();
                    for (int i = 1; i <= 6;) {
                        String nomePkmn = InputUtils.lerString("Insira o nome do Pokemon desejado ou 0 para fechar a lista de Pokemons (min.1, max.6): ", sc);
                        if (nomePkmn.equalsIgnoreCase("0") && !membros.isEmpty()) break;
                        try {
                            Pokemon pokemon = servPkmn.buscarPorNome(nomePkmn);
                            membros.add(pokemon);
                            System.out.println("Pokemon " + pokemon.getName() + " adicionado a equipe com sucesso!");
                            i++;
                        } catch (PokemonNaoEncontradoException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    int id = servTeam.gerarNovoId();
                    try {
                        Team t = new TeamBuilder()
                                        .id(id)
                                        .nome(nome)
                                        .pokemons(membros)
                                        .build();
                        if (teams == null || teams.isEmpty()) teams.add(t);
                        servTeam.criarTeam(id, nome, membros);
                        OutputUtils.slowPrint("Equipe " + t.getName() + " criada com sucesso!", 50);
                        System.out.println("Pokemons membros:");
                        int i = 0;
                        for (Pokemon pkmn: t.getPokemons()) {
                            if (i > 0) System.out.print("/");
                            System.out.print(pkmn.getName() + " (#" + String.format("%04d", pkmn.getId()) + ")");
                            i++;
                        }
                        System.out.println("\nBST medio: " + t.baseStatTotalMedio());
                    } catch (DadoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 12:
                    List<Team> listaTeams = servTeam.listarTeams();
                    if (!listaTeams.isEmpty()) {
                        OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        for (Team team : listaTeams) {
                            System.out.println("Dados da equipe " + team.getName() + ":");
                            System.out.println("Pokemons membros:");
                            int i = 0;
                            for (Pokemon pkmn: team.getPokemons()) {
                                if (i > 0) System.out.print("/");
                                System.out.print(pkmn.getName() + " (#" + String.format("%04d", pkmn.getId()) + ")");
                                i++;
                            }
                            System.out.println("\nBST medio: " + team.baseStatTotalMedio());
                            OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        }
                    } else System.out.println("Nao ha equipes para listar!");
                    break;
                case 13:
                    sc.nextLine();
                    nomeBusca = InputUtils.lerString("Insira o nome da equipe para procura: ", sc);
                    nomeBusca = Pattern
                                    .compile("\\b(\\w)")
                                    .matcher(nomeBusca)
                                    .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Team team = servTeam.buscarPorNome(nomeBusca);
                        Menu.exibirMenuEquipe(team, 50);
                        OutputUtils.slowPrint("Equipe " + nomeBusca + " encontrada com sucesso!", 50);                  
                    } catch (TeamNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 14:
                    sc.nextLine();
                    String nomeEquipe = InputUtils.lerString("Insira o nome da equipe para procura: ", sc);
                    nomeEquipe = Pattern
                                        .compile("\\b(\\w)")
                                        .matcher(nomeEquipe)
                                        .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Team team = servTeam.buscarPorNome(nomeEquipe);                    
                        while (true) {
                            Menu.exibirMenuModificacaoEquipe(20);
                            boolean flag = false;
                            String optionModify = InputUtils.lerString("Insira a opcao desejada desejado: ", sc);
                            switch (optionModify.toUpperCase()) {
                                case "A":
                                    String nomePkmn = InputUtils.lerString("Insira o nome do Pokemon a ser adicionado: ", sc);
                                    try {
                                        Pokemon pokemon = servPkmn.buscarPorNome(nomePkmn);
                                        team.addPokemon(pokemon);
                                        System.out.println("Pokemon " + pokemon.getName() + " adicionado a equipe com sucesso!");
                                    } catch (PokemonNaoEncontradoException e) {
                                        System.out.println(e.getMessage());
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case "B":
                                    nomePkmn = InputUtils.lerString("Insira o nome do Pokemon a ser removido: ", sc);
                                    try {
                                        Pokemon pokemon = servPkmn.buscarPorNome(nomePkmn);
                                        team.removePokemon(pokemon.getName());
                                        System.out.println("Pokemon " + pokemon.getName() + " removido da equipe com sucesso!");
                                    } catch (PokemonNaoEncontradoException e) {
                                        System.out.println(e.getMessage());
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case "0":
                                    flag = true;
                                    break;
                                default: System.out.println("Opcao invalida!");
                            }
                            if (flag) break;
                        }
                    } catch (TeamNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 15:
                    sc.nextLine();
                    nomeRemocao = InputUtils.lerString("Insira o nome do golpe para procura: ", sc);
                    nomeRemocao = Pattern
                                    .compile("\\b(\\w)")
                                    .matcher(nomeRemocao)
                                    .replaceAll(m -> m.group(1).toUpperCase());
                    try {
                        Team team = servTeam.buscarPorNome(nomeRemocao);  
                        String optionRemocao = InputUtils.lerString("Certeza que deseja remover " + team.getName() + "? (S/N | Esta acao nao tem volta): ", sc);
                        while (!optionRemocao.equalsIgnoreCase("s") && !optionRemocao.equalsIgnoreCase("n")) {
                            optionRemocao = InputUtils.lerString("Opcao invalida!\nCerteza que deseja remover " + team.getName() + "? (S/N | Esta acao nao tem volta): ", sc);
                        }
                        if (optionRemocao.equalsIgnoreCase("s")) {
                            servTeam.removerTeam(team.getName());
                            OutputUtils.slowPrint("Equipe " + nomeRemocao + " removida com sucesso!", 50);
                        }                  
                    } catch (TeamNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 16:
                    if (pokemons == null || pokemons.isEmpty()) {
                        System.out.println("Nao ha Pokemons para simular a batalha!\n(Operacao abortada)");
                        break;
                    }
                    if (moves == null || moves.isEmpty()) {
                        System.out.println("Nao ha golpes para simular a batalha!\n(Operacao abortada)");
                        break;
                    }
                    sc.nextLine();
                    String nomeP1 = InputUtils.lerString("Insira um nome para procura de um Pokemon: ", sc);
                    String nomeP2 = InputUtils.lerString("Insira um nome para procura do Pokemon oponente: ", sc);
                    try {
                        Pokemon p1 = servPkmn.buscarPorNome(nomeP1);
                        Pokemon p2 = servPkmn.buscarPorNome(nomeP2);
                        List<Move> movesP1 = new ArrayList<>();
                        List<Move> movesP2 = new ArrayList<>();
                        for (int i = 1; i <= 4;) {
                            String move1 = InputUtils.lerString("Insira o " + i + "o golpe de " + p1.getName() + " ou 0 para fechar a lista de golpes (min.1, max.4): ", sc);
                            if (move1.equalsIgnoreCase("0") && !movesP1.isEmpty()) break;
                            try {
                                Move moveP1 = servMove.buscarPorNome(move1);
                                movesP1.add(moveP1);
                                System.out.println("Golpe " + moveP1.getName() + " adicionado a " + p1.getName() + " com sucesso!");
                                i++;
                            } catch (MoveNaoEncontradoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String nature1 = InputUtils.lerString("Insira a nature de " + p1.getName() + ": ", sc);
                        int nivelP1 = InputUtils.lerInt("Insira o nivel de " + p1.getName() + ": ", sc);
                        sc.nextLine();
                        for (int i = 1; i <= 4;) {
                            String move2 = InputUtils.lerString("Insira o " + i + "o golpe de " + p2.getName() + " ou 0 para fechar a lista de golpes (min.1, max.4): ", sc);
                            if (move2.equalsIgnoreCase("0") && !movesP2.isEmpty()) break;
                            try {
                                Move moveP2 = servMove.buscarPorNome(move2);
                                movesP2.add(moveP2);
                                System.out.println("Golpe " + moveP2.getName() + " adicionado a " + p2.getName() + " com sucesso!");
                                i++;
                            } catch (MoveNaoEncontradoException e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        String nature2 = InputUtils.lerString("Insira a nature de " + p2.getName() + ": ", sc);
                        int nivelP2 = InputUtils.lerInt("Insira o nivel de " + p2.getName() + ": ", sc);
                         try {
                            Nature natureP1 = Nature.fromString(nature1);
                            p1.setMoves(movesP1);
                            p1.setNature(natureP1);
                            p1.setLevel(nivelP1);
                            p1.setOwnStats(p1.getLevel(), p1.getNature());
                            Nature natureP2 = Nature.fromString(nature2);
                            p2.setMoves(movesP2);
                            p2.setNature(natureP2);
                            p2.setLevel(nivelP2);
                            p2.setOwnStats(p2.getLevel(), p2.getNature());
                            Pokemon vencedor = servBattle.batalhar(p1, p2, sc);
                            OutputUtils.slowPrint("O Pokemon vencedor foi " + vencedor.getName() + "!", 50);
                        } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel executar a batalha: " + e.getMessage());
                            break;
                        }
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println("Nao foi possivel executar a batalha: " + e.getMessage());
                    }
                    break;
                case 17:
                    if (pokemons == null || pokemons.isEmpty()) {
                        System.out.println("Nao ha Pokemons para analisar estatisticas!\n(Operacao abortada)");
                        break;
                    }
                    int optionStatistics = -1;
                    do {
                        Menu.exibirMenuEstatisticasPokemons(20);
                        optionStatistics = InputUtils.lerInt("Insira a opcao desejada: ", sc);
                        switch (optionStatistics) {
                            case 1:
                                sc.nextLine();
                                String optionStat = InputUtils.lerString("Insira o stat desejado: ", sc);
                                try {
                                    Pokemon pkmn = servPkmn.maiorStat(optionStat);
                                    System.out.println("Pokemon de maior " + optionStat.toLowerCase() + ": " + pkmn.getName());
                                    System.out.println("Valor do stat " + optionStat.toLowerCase() + ": " + pkmn.getStatFromString(optionStat));
                                } catch (PokemonNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                sc.nextLine();
                                optionStat = InputUtils.lerString("Insira o stat desejado: ", sc);
                                try {
                                    Pokemon pkmn = servPkmn.menorStat(optionStat);
                                    System.out.println("Pokemon de menor " + optionStat.toLowerCase() + ": " + pkmn.getName());
                                    System.out.println("Valor do stat " + optionStat.toLowerCase() + ": " + pkmn.getStatFromString(optionStat));
                                } catch (PokemonNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 0: break;
                            default: System.out.println("Opcao invalida!");
                        }
                    } while (optionStatistics != 0);
                    break;
                case 18:
                    if (teams == null || teams.isEmpty()) {
                        System.out.println("Nao ha equipes para analisar estatisticas!\n(Operacao abortada)");
                        break;
                    }
                    optionStatistics = -1;
                    do {
                        Menu.exibirMenuEstatisticasEquipes(20);
                        optionStatistics = InputUtils.lerInt("Insira a opcao desejada: ", sc);
                        switch (optionStatistics) {
                            case 1:
                                try {
                                    Team team = servTeam.maiorBaseStatTotalMedio();
                                    System.out.println("Equipe de maior BST medio: " + team.getName());
                                    System.out.println("Valor do BST medio: " + team.baseStatTotalMedio());
                                } catch (TeamNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 2:
                                try {
                                    Team team = servTeam.menorBaseStatTotalMedio();
                                    System.out.println("Equipe de menor BST medio: " + team.getName());
                                    System.out.println("Valor do BST medio: " + team.baseStatTotalMedio());
                                } catch (TeamNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 3:
                                sc.nextLine();
                                String optionStat = InputUtils.lerString("Insira o stat desejado: ", sc);
                                try {
                                    Team team = servTeam.maiorStatMedio(optionStat);
                                    System.out.println("Equipe de maior " + optionStat.toLowerCase() + " medio: " + team.getName());
                                    System.out.println("Valor do stat "+ optionStat.toLowerCase() + " medio: " + team.statMedio(optionStat));
                                } catch (TeamNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 4:
                                sc.nextLine();
                                optionStat = InputUtils.lerString("Insira o stat desejado: ", sc);
                                try {
                                    Team team = servTeam.menorStatMedio(optionStat);
                                    System.out.println("Equipe de menor " + optionStat.toLowerCase() + " medio: " + team.getName());
                                    System.out.println("Valor do stat "+ optionStat.toLowerCase() + " medio: " + team.statMedio(optionStat));
                                } catch (TeamNaoEncontradoException e) {
                                    System.out.println(e.getMessage());
                                } catch (DadoInvalidoException e) {
                                    System.out.println(e.getMessage());
                                }
                                break;
                            case 0: break;
                            default: System.out.println("Opcao invalida!");
                        }
                    } while (optionStatistics != 0);
                    break;
                case 19:
                     try {
                        String optionDelete = InputUtils.lerString("Tem certeza que deseja limpar os arquivos? (S/N | Esta acao nao tem volta): ", sc);
                        while (!optionDelete.equalsIgnoreCase("s") && !optionDelete.equalsIgnoreCase("n")) {
                            optionDelete = InputUtils.lerString("Opcao invalida!\nTem certeza que deseja limpar os arquivos? (S/N | Esta acao nao tem volta): ", sc);
                        }
                        if (optionDelete.equalsIgnoreCase("s")) {
                            repoPkmn.limparArquivo();
                            repoMove.limparArquivo();
                            String txt = servPkmn.contarListaPokemons() + " Pokemons e " + servMove.contarListaMoves() + " golpes foram removidos com sucesso!";
                            OutputUtils.slowPrint(txt, 50);
                        }
                    } catch (IOException e) {
                        System.out.println("Nao foi possivel limpar o arquivo!");
                    }
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
        } while (optionMenu != 0);
        try {
            repoPkmn.escreverArquivo(pokemons);
            repoMove.escreverArquivo(moves);
            repoTeam.escreverArquivo(teams);
            OutputUtils.slowPrint(servPkmn.contarListaPokemons() + " Pokemons, " + servMove.contarListaMoves() + " golpes e " + 
                                    servTeam.contarListaTeams() + " equipes foram salvos com sucesso!", 50);
        } catch (IOException e) {
            System.out.println("Nao foi possivel escrever nos arquivos!");
        }
        System.out.print("Encerrando o programa");
        Thread.sleep(500);
        OutputUtils.slowPrint("...", 150);
        System.out.println();
        sc.close();
    }
}
