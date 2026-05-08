package pokedex.service;

import java.util.List;
import java.util.Scanner;

import pokedex.domain.enums.MoveCategory;
import pokedex.domain.enums.Typing;
import pokedex.domain.model.Move;
import pokedex.domain.model.Pokemon;
import pokedex.util.InputUtils;
import pokedex.util.OutputUtils;

public class BattleService {
    private TypeEffectivenessService effect;
    public BattleService(TypeEffectivenessService effect) {
        this.effect = effect;
    }
    public Pokemon batalhar(Pokemon p1, Pokemon p2, Scanner sc) throws InterruptedException {
        int turno = 0;
        Pokemon primeiro = definirPrimeiro(p1, p2);
        Pokemon segundo = definirSegundo(p1, p2);
        int vidaP1 = primeiro.getOwnStats().getHp();
        int vidaP2 = segundo.getOwnStats().getHp();
        OutputUtils.slowPrint("---------------------------------------------------------", 50);
        OutputUtils.slowPrint("Batalha entre " + primeiro.getName() + " e " + segundo.getName() + " iniciada!", 50);
        while (true) {
            turno++;
            System.out.print("\n");
            System.out.println(primeiro.getName() + " Lv." + primeiro.getLevel() + ": " + vidaP1 + " / " + primeiro.getOwnStats().getHp());
            System.out.println(segundo.getName() + " Lv." + segundo.getLevel() + ": " + vidaP2 + " / " + segundo.getOwnStats().getHp());
            System.out.print("\n");
            System.out.println("Golpes de " + primeiro.getName() + ":");
            int i = 1;
            for (Move m : primeiro.getMoves()) {
                System.out.println(i + ". " + m.getName());
                i++;
            }
            int opcaoGolpe = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            while (opcaoGolpe < 1 || opcaoGolpe > 4) opcaoGolpe = InputUtils.lerInt("Opcao invalida!\nInsira uma das opcoes acima: ", sc);
            int indexGolpes = opcaoGolpe - 1;
            Move golpeP1 = primeiro.getMoves().get(indexGolpes);
            System.out.print("\n");
            System.out.println("Golpes de " + segundo.getName() + ":");
            i = 1;
            for (Move m : segundo.getMoves()) {
                System.out.println(i + ". " + m.getName());
                i++;
            }
            opcaoGolpe = InputUtils.lerInt("Insira uma das opcoes acima: ", sc);
            while (opcaoGolpe < 1 || opcaoGolpe > 4) opcaoGolpe = InputUtils.lerInt("Opcao invalida! Insira uma das opcoes acima: ", sc);
            indexGolpes = opcaoGolpe - 1;
            Move golpeP2 = segundo.getMoves().get(indexGolpes);
            double danoP1 = (golpeP1.getCategory() == MoveCategory.PHYSICAL) ? calcularDanoFisico(primeiro, segundo, golpeP1) : calcularDanoEspecial(primeiro, segundo, golpeP1);
            danoP1 += calcularSTAB(primeiro, golpeP1, danoP1);
            danoP1 *= calcularEficaciaDeTipo(golpeP1.getType(), segundo.getTypes());
            if (calcularEficaciaDeTipo(golpeP1.getType(), segundo.getTypes()) <= 0.5) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " nao foi muito eficaz!", 50);
            else if (calcularEficaciaDeTipo(golpeP1.getType(), segundo.getTypes()) >= 2) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " foi super-eficaz!", 50);
            int hpPerdido = (vidaP2 < (int)danoP1) ? vidaP2 : (int)danoP1;
            if (hpPerdido == 0) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " nao fez efeito em " + segundo.getName() + "!", 50);
            else OutputUtils.slowPrint("\nO Pokemon " + segundo.getName() + " perdeu " + hpPerdido + " pontos de vida!", 50);
            vidaP2 -= (int)danoP1;
            if (vidaP2 <= 0) {
                if (hpPerdido == segundo.getOwnStats().getHp()) OutputUtils.slowPrint("\nO golpe " + golpeP1 + " foi um OH-KO!", 50);
                if (turno > 1) OutputUtils.slowPrint("\nO Pokemon " + segundo.getName() + " desmaiou em " + turno + " turnos!", 50);
                OutputUtils.slowPrint("---------------------------------------------------------", 50);
                return primeiro;
            }
            double danoP2 = (golpeP2.getCategory() == MoveCategory.PHYSICAL) ? calcularDanoFisico(segundo, primeiro, golpeP2) : calcularDanoEspecial(segundo, primeiro, golpeP2);
            danoP2 += calcularSTAB(segundo, golpeP2, danoP2);
            danoP2 *= calcularEficaciaDeTipo(golpeP2.getType(), primeiro.getTypes());
            if (calcularEficaciaDeTipo(golpeP2.getType(), primeiro.getTypes()) <= 0.5) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " nao foi muito eficaz!", 50);
            else if (calcularEficaciaDeTipo(golpeP2.getType(), primeiro.getTypes()) >= 2) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " foi super-eficaz!", 50);
            hpPerdido = (vidaP1 < (int)danoP2) ? vidaP1 : (int)danoP2;
            if (hpPerdido == 0) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " nao fez efeito em " + primeiro.getName() + "!", 50);
            else OutputUtils.slowPrint("\nO Pokemon " + primeiro.getName() + " perdeu " + hpPerdido + " pontos de vida!", 50);
            vidaP1 -= (int)danoP2;
            if (vidaP1 <= 0) {
                if (hpPerdido == primeiro.getOwnStats().getHp()) OutputUtils.slowPrint("\nO golpe " + golpeP2 + " foi um OH-KO!", 50);
                if (turno > 1) OutputUtils.slowPrint("\nO Pokemon " + primeiro.getName() + " desmaiou em " + turno + " turnos!", 50);
                OutputUtils.slowPrint("---------------------------------------------------------", 50);
                return segundo;
            }
        }
    }
    public double calcularEficaciaDeTipo(Typing atacante, List<Typing> defensor) {
        double multiplicador = 1.0;
        for (Typing t : defensor) {
            multiplicador *= effect.getMultiplicador(atacante, t);
        }
        return multiplicador;
    }
    public double calcularSTAB(Pokemon atacante, Move golpe, double dano) {
        for (Typing t : atacante.getTypes()) {
            if (golpe.getType() == t) return dano * 0.5;
        }
        return 0.0;
    }
    public double calcularDanoFisico(Pokemon atacante, Pokemon defensor, Move golpe) {
        double d1 = ((2 * atacante.getLevel()) / 5) + 2;
        double d2 = (golpe.getDamage() * atacante.getOwnStats().getAttack()) / defensor.getOwnStats().getDefense();
        return ((d1 * d2) / 50) + 2;
    }
    public double calcularDanoEspecial(Pokemon atacante, Pokemon defensor, Move golpe) {
        double d1 = ((2 * atacante.getLevel()) / 5) + 2;
        double d2 = (golpe.getDamage() * atacante.getOwnStats().getSpecialAttack()) / defensor.getOwnStats().getSpecialDefense();
        return ((d1 * d2) / 50) + 2;
    }
    public Pokemon definirPrimeiro(Pokemon p1, Pokemon p2) {
        return (p1.getOwnStats().getSpeed() >= p2.getOwnStats().getSpeed()) ? p1 : p2;
    }
    public Pokemon definirSegundo(Pokemon p1, Pokemon p2) {
        return (definirPrimeiro(p1, p2) == p1) ? p2 : p1;
    }
}
