package pokedex.app;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import pokedex.builder.*;
import pokedex.domain.enums.*;
import pokedex.domain.model.*;
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
        String optionArquivo;
        List<Pokemon> pokemons = new ArrayList<>();
        List<Move> moves = new ArrayList<>();
        File arquivoPkmn = new File("pokedex/dataset/files/pokemons.csv");
        File arquivoMove = new File("pokedex/dataset/files/moves.csv");
        FilePokemonRepository repoPkmn = new FilePokemonRepository("pokedex/dataset/files/pokemons.csv");
        FileMoveRepository repoMove = new FileMoveRepository("pokedex/dataset/files/moves.csv");
        PokemonService servPkmn = new PokemonService(repoPkmn);
        MoveService servMove = new MoveService(repoMove);
        TypeEffectivenessService effect = new TypeEffectivenessService("pokedex/repository/data/matchups.txt");
        BattleService servBattle = new BattleService(effect);
        try {
            effect.extrairDeArquivo();
        } catch (IOException e) {
            System.out.println("Nao foi possivel carregar o arquivo!" + "(" + e.getMessage() + ")");
        }
        if (arquivoMove.exists() && arquivoMove.length() > 0) {
            moves = repoMove.lerArquivo();
            repoMove.inserirMoves(moves);
            servMove.putMoves(moves);
        }
        if (arquivoPkmn.exists() && arquivoPkmn.length() > 0) {
            optionArquivo = InputUtils.lerString("Deseja carregar os Pokemons do arquivo? (S/N): ", sc);
            while (!optionArquivo.equalsIgnoreCase("s") && !optionArquivo.equalsIgnoreCase("n")) {
                optionArquivo = InputUtils.lerString("Opcao invalida!\nDeseja carregar os Pokemons do arquivo? (S/N): ", sc);
            }
            if (optionArquivo.equalsIgnoreCase("s")) {
                pokemons = repoPkmn.lerArquivo();
                repoPkmn.inserirPokemons(pokemons);
                servPkmn.putPokemons(pokemons);
                OutputUtils.slowPrint(repoPkmn.contarPokemons() + " Pokemons foram carregados com sucesso!", 50);
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
                    nome = nome
                            .substring(0, 1)
                            .toUpperCase() + nome.substring(1);
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
                    nomeBusca = nomeBusca.substring(0, 1).toUpperCase() + nomeBusca.substring(1);
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
                    String nomeEdicao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    try {
                        Pokemon pkmn = servPkmn.buscarPorNome(nomeEdicao);
                        int edicao = -1;
                        do {
                            Menu.exibirMenuEdicaoPokemon(20);
                            edicao = InputUtils.lerInt("Insira a opcao de edicao desejada: ", sc);
                            switch (edicao) {
                                case 1:
                                    sc.nextLine();
                                    String novoNome = InputUtils.lerString("Insira o novo nome do Pokemon: ", sc);
                                    while (novoNome == null || novoNome.isEmpty()) {
                                        novoNome = InputUtils.lerString("Entrada invalida!\nInsira o novo nome do Pokemon: ", sc);
                                    }
                                    novoNome = novoNome
                                                    .substring(0, 1)
                                                    .toUpperCase() + novoNome.substring(1);
                                    pkmn.setName(novoNome);
                                    OutputUtils.slowPrint("Nome alterado com sucesso!", 50);
                                    break;
                                case 2:
                                    sc.nextLine();
                                    String novoTipo1 = InputUtils.lerString("Insira o novo tipo principal do Pokemon: ", sc);
                                    List<Typing> novosTiposPkmn = new ArrayList<>();
                                    try {
                                        Typing novoTipoPkmn1 = Typing.fromString(novoTipo1);
                                        novosTiposPkmn.add(novoTipoPkmn1);
                                        String optionTipoSec = InputUtils.lerString("Deseja inserir/modificar um tipo secundario? (S/N): ", sc);
                                        while (!optionTipoSec.equalsIgnoreCase("s") && !optionTipoSec.equalsIgnoreCase("n")) {
                                            optionTipoSec = InputUtils.lerString("Opcao invalida!\nDeseja inserir um tipo secundario? (S/N): ", sc);
                                        }
                                        if (optionTipoSec.equalsIgnoreCase("s")) {
                                            String novoTipo2 = InputUtils.lerString("Insira o novo tipo secundario do Pokemon: ", sc);
                                            Typing novoTipoPkmn2 = Typing.fromString(novoTipo2);
                                            novosTiposPkmn.add(novoTipoPkmn2);
                                        }
                                        pkmn.setTypes(novosTiposPkmn);
                                        OutputUtils.slowPrint("Tipo(s) alterado(s) com sucesso!", 50);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 3:
                                    int novoHp = InputUtils.lerInt("Insira o novo HP base: ", sc);
                                    int novoAtaque = InputUtils.lerInt("Insira o novo ataque base: ", sc);
                                    int novaDefesa = InputUtils.lerInt("Insira a nova defesa base: ", sc);
                                    int novoAtaqueEspecial = InputUtils.lerInt("Insira o novo ataque especial base: ", sc);
                                    int novaDefesaEspecial = InputUtils.lerInt("Insira a nova defesa especial base: ", sc);
                                    int novaVelocidade = InputUtils.lerInt("Insira a nova velocidade base: ", sc);
                                    try {
                                        Stats novosBaseStats = new StatsBuilder()
                                                                            .hp(novoHp)
                                                                            .ataque(novoAtaque)
                                                                            .defesa(novaDefesa)
                                                                            .ataqueEspecial(novoAtaqueEspecial)
                                                                            .defesaEspecial(novaDefesaEspecial)
                                                                            .velocidade(novaVelocidade)
                                                                            .build();
                                        pkmn.setBaseStats(novosBaseStats);
                                        OutputUtils.slowPrint("Stats base alterados com sucesso!", 50);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 0: break;
                                default: System.out.println("Opcao invalida!");
                            }
                        } while (edicao != 0);
                        int i = 0;
                        for (Pokemon p : pokemons) {
                            if (p.getId() == pkmn.getId()) break;
                            i++;
                        }
                        pokemons.set(i, pkmn);
                    } catch (PokemonNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 6:
                    sc.nextLine();
                    String nomeRemocao = InputUtils.lerString("Insira o nome do Pokemon para procura: ", sc);
                    nomeRemocao = nomeRemocao
                                        .substring(0, 1)
                                        .toUpperCase() + nomeRemocao.substring(1);
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
                case 7:
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
                    int dano = InputUtils.lerInt("Insira o dano base do golpe: ", sc);
                    sc.nextLine();
                    String categoriaMove = InputUtils.lerString("Insira a categoria do golpe (fisico/especial): ", sc);
                    try {
                        Typing tipo = Typing.fromString(tipoMove);
                        MoveCategory categoria = MoveCategory.fromString(categoriaMove);
                        int id = servMove.gerarNovoId();
                        Move m = new MoveBuilder()
                                            .id(id)
                                            .nome(nome)
                                            .tipo(tipo)
                                            .dano(dano)
                                            .categoria(categoria)
                                            .build();
                        if (moves == null || moves.isEmpty()) moves.add(m);
                        servMove.registrarMove(id, nome, tipo, dano, categoria);
                        OutputUtils.slowPrint("Golpe " + m.getName() + " registrado com sucesso!", 50);
                        System.out.println("Tipo: " + m.getType());
                        System.out.println("Dano base: " + m.getDamage());
                        System.out.println("Categoria: " + m.getCategory());
                    } catch (DadoInvalidoException e) {
                            System.out.println("Nao foi possivel registrar o golpe: " + e.getMessage());
                    }
                    break;
                case 8:
                    List<Move> listaMoves= servMove.listarMoves();
                    if (!listaMoves.isEmpty()) {
                        OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        for (Move move : listaMoves) {
                            System.out.println("Dados do golpe " + move.getName() + ":");
                            System.out.println("Tipo: " + move.getType());
                            System.out.println("Dano base: " + move.getDamage());
                            System.out.println("Categoria: " + move.getCategory());
                            OutputUtils.slowPrint("---------------------------------------------------------", 10);
                        }
                        OutputUtils.slowPrint(servMove.contarListaMoves() + " golpes listados com sucesso!", 50);
                    } else System.out.println("Nao ha golpes para listar!");
                    break;
                case 9:
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
                case 10:
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
                                System.out.println("Dano base: " + move.getDamage());
                                System.out.println("Categoria: " + move.getCategory());
                                OutputUtils.slowPrint("---------------------------------------------------------", 10);
                            }
                            OutputUtils.slowPrint(listaMoves.size() + " golpes listados com sucesso!", 50);
                        } else System.out.println("Nao ha golpes de tipo " + tipo + " para listar!");
                    } catch (DadoInvalidoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 11:
                    sc.nextLine();
                    nomeEdicao = InputUtils.lerString("Insira o nome do golpe para procura: ", sc);
                    try {
                        Move move = servMove.buscarPorNome(nomeEdicao);
                        int edicao = -1;
                        do {
                            Menu.exibirMenuEdicaoGolpe(15);
                            edicao = InputUtils.lerInt("Insira a opcao de edicao desejada: ", sc);
                            switch (edicao) {
                                case 1:
                                    sc.nextLine();
                                    String novoNome = InputUtils.lerString("Insira o novo nome do golpe: ", sc);
                                    while (novoNome == null || novoNome.isEmpty()) {
                                        novoNome = InputUtils.lerString("Entrada invalida!\nInsira o novo nome do golpe: ", sc);
                                    }
                                    novoNome = Pattern
                                                    .compile("\\b(\\w)")
                                                    .matcher(novoNome)
                                                    .replaceAll(m -> m.group(1).toUpperCase());
                                    move.setName(novoNome);
                                    OutputUtils.slowPrint("Nome alterado com sucesso!", 50);
                                    break;
                                case 2:
                                    sc.nextLine();
                                    String novoTipo = InputUtils.lerString("Insira o novo tipo do golpe: ", sc);
                                    try {
                                        Typing novoTipoMove = Typing.fromString(novoTipo);
                                        move.setType(novoTipoMove);
                                        OutputUtils.slowPrint("Tipo alterado com sucesso!", 50);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 3:
                                    int novoDanoBase = InputUtils.lerInt("Insira o novo dano base do golpe: ", sc);
                                    try {
                                        move.setDamage(novoDanoBase);
                                        OutputUtils.slowPrint("Dano base alterado com sucesso!", 50);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 4:
                                    sc.nextLine();
                                    String novaCategoria = InputUtils.lerString("Insira a nova categoria do golpe: ", sc);
                                    try {
                                        MoveCategory novaCategoriaMove = MoveCategory.fromString(novaCategoria);
                                        move.setCategory(novaCategoriaMove);
                                        OutputUtils.slowPrint("Categoria alterada com sucesso!", 50);
                                    } catch (DadoInvalidoException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    break;
                                case 0: break;
                                default: System.out.println("Opcao invalida!");
                            }
                        } while (edicao != 0);
                        int i = 0;
                        for (Move m : moves) {
                            if (m.getId() == move.getId()) break;
                            i++;
                        }
                        moves.set(i, move);
                    } catch (MoveNaoEncontradoException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 12:
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
                case 13:
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
                            if (move1.equalsIgnoreCase("0") && !move1.isEmpty()) break;
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
                            if (move2.equalsIgnoreCase("0") && !move2.isEmpty()) break;
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
                case 14:
                    if (pokemons == null || pokemons.isEmpty()) {
                        System.out.println("Nao ha Pokemons para analisar estatisticas!\n(Operacao abortada)");
                        break;
                    }
                    int optionStatistics = -1;
                    do {
                        Menu.exibirMenuEstatisticas(20);
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
                case 15:
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
        sc.nextLine();
        optionArquivo = InputUtils.lerString("\nDeseja salvar os Pokemons e golpes nos respectivos arquivos? (S/N): ", sc);
        while (!optionArquivo.equalsIgnoreCase("s") && !optionArquivo.equalsIgnoreCase("n")) {
            optionArquivo = InputUtils.lerString("Opcao invalida!\nDeseja salvar os Pokemons e golpes nos respectivos arquivos? (S/N): ", sc);
        }
        if (optionArquivo.equalsIgnoreCase("s")) {
            try {
                repoPkmn.escreverArquivo(pokemons);
                repoMove.escreverArquivo(moves);
                OutputUtils.slowPrint(servPkmn.contarListaPokemons() + " Pokemons e " + servMove.contarListaMoves() + " golpes foram salvos com sucesso!", 50);
            } catch (IOException e) {
                System.out.println("Nao foi possivel escrever nos arquivos!");
            }
        }
        System.out.print("Encerrando o programa");
        Thread.sleep(500);
        OutputUtils.slowPrint("...", 150);
        System.out.println();
        sc.close();
    }
}
