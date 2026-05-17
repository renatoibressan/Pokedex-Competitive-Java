package pokedex.service;

import java.util.List;
import java.util.Scanner;

import pokedex.domain.enums.DamagingMoveCategory;
import pokedex.domain.enums.Target;
import pokedex.domain.enums.Typing;
import pokedex.domain.models.DamagingMove;
import pokedex.domain.models.Move;
import pokedex.domain.models.Pokemon;
import pokedex.domain.models.StatStages;
import pokedex.domain.models.Stats;
import pokedex.domain.models.StatusMove;
import pokedex.domain.records.EffectSet;
import pokedex.exception.DadoInvalidoException;
import pokedex.util.InputUtils;
import pokedex.util.OutputUtils;

public class BattleService {
    private TypeEffectivenessService effect;
    public BattleService(TypeEffectivenessService effect) {
        this.effect = effect;
    }
    public Pokemon batalhar(Pokemon p1, Pokemon p2, Scanner sc)  throws DadoInvalidoException, InterruptedException {
        int turnos = 0;
        Pokemon first = definirPrimeiro(p1, p2);
        Pokemon second = definirSegundo(p1, p2);
        Pokemon primeiro = new Pokemon(first);
        Pokemon segundo = new Pokemon(second);
        int vidaP1 = primeiro.getOwnStats().getHp();
        int vidaP2 = segundo.getOwnStats().getHp();
        OutputUtils.slowPrint("---------------------------------------------------------", 50);
        OutputUtils.slowPrint("Batalha entre " + primeiro.getName() + " e " + segundo.getName() + " iniciada!", 50);
        while (true) {
            turnos++;
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
            if (golpeP1 instanceof DamagingMove golpeDano) {
                double dano = calcularDanoCompleto(primeiro, segundo, golpeDano);
                int hpPerdido = (vidaP2 < (int)dano) ? vidaP2 : (int)dano;
                if (hpPerdido == 0) OutputUtils.slowPrint("\nO golpe " + golpeP1.getName() + " nao fez efeito em " + segundo.getName() + "!", 50);
                else OutputUtils.slowPrint("\nO Pokemon " + segundo.getName() + " perdeu " + hpPerdido + " pontos de vida!", 50);
                vidaP2 -= (int)dano;
                if (vidaP2 <= 0) {
                    if (hpPerdido == segundo.getOwnStats().getHp()) OutputUtils.slowPrint("\nO golpe " + golpeP1.getName() + " foi um OH-KO!", 50);
                    if (turnos > 1) OutputUtils.slowPrint("\nO Pokemon " + segundo.getName() + " desmaiou em " + turnos + " turnos!", 50);
                    OutputUtils.slowPrint("---------------------------------------------------------", 50);
                    return primeiro;
                }
            } else if (golpeP1 instanceof StatusMove golpeStatus) {
                modificarAtributo(primeiro, segundo, golpeStatus.getEffectSet());
            }
            if (golpeP2 instanceof DamagingMove golpeDano) {
                double dano = calcularDanoCompleto(segundo, primeiro, golpeDano);
                int hpPerdido = (vidaP1 < (int)dano) ? vidaP1 : (int)dano;
                if (hpPerdido == 0) OutputUtils.slowPrint("\nO golpe " + golpeP2.getName() + " nao fez efeito em " + primeiro.getName() + "!", 50);
                else OutputUtils.slowPrint("\nO Pokemon " + primeiro.getName() + " perdeu " + hpPerdido + " pontos de vida!", 50);
                vidaP1 -= (int)dano;
                if (vidaP1 <= 0) {
                    if (hpPerdido == primeiro.getOwnStats().getHp()) OutputUtils.slowPrint("\nO golpe " + golpeP2.getName() + " foi um OH-KO!", 50);
                    if (turnos > 1) OutputUtils.slowPrint("\nO Pokemon " + primeiro.getName() + " desmaiou em " + turnos + " turnos!", 50);
                    OutputUtils.slowPrint("---------------------------------------------------------", 50);
                    return segundo;
                }
            } else if (golpeP2 instanceof StatusMove golpeStatus) {
                modificarAtributo(segundo, primeiro, golpeStatus.getEffectSet());
            }
        }
    }
    public void modificarAtributo(Pokemon self, Pokemon foe, EffectSet effectSet) throws DadoInvalidoException, InterruptedException {
        Pokemon target = (effectSet.target() == Target.SELF) ? self : foe;
        Stats stats = target.getOwnStats();
        int valor = effectSet.modifier();
        switch (effectSet.stat()) {
            case ATTACK:
                int anterior = stats.getStages().getAttackStage();
                stats.getStages().addAttackStage(valor);
                int atkBase = stats.getAttack();
                int novoAtk = novoAtaque(stats.getStages(), atkBase);
                stats.setAttack(novoAtk);
                if (novoAtk > atkBase) {
                    if (anterior + valor > 6) OutputUtils.slowPrint("\nNao foi possivel aumentar o ataque de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nAtaque de " + target.getName() + " aumentado em " + valor + " estagios!", 50);
                } else if (novoAtk < atkBase) {
                    if (anterior + valor < -6) OutputUtils.slowPrint("\nNao foi possivel reduzir o ataque de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nAtaque de " + target.getName() + " reduzido em " + valor + " estagios!", 50);
                }
                break;
            case DEFENSE:
                anterior = stats.getStages().getDefenseStage();
                stats.getStages().addDefenseStage(valor);
                int defBase = stats.getDefense();
                int novaDef = novaDefesa(stats.getStages(), defBase);
                stats.setDefense(novaDef);
                if (novaDef > defBase) {
                    if (anterior + valor > 6) OutputUtils.slowPrint("\nNao foi possivel aumentar a defesa de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nDefesa de " + target.getName() + " aumentada em " + valor + " estagios!", 50);
                } else if (novaDef < defBase) {
                    if (anterior + valor < -6) OutputUtils.slowPrint("\nNao foi possivel reduzir a defesa de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nDefesa de " + target.getName() + " reduzida em " + valor + " estagios!", 50);
                }
                break;
            case SPECIAL_ATTACK:
                anterior = stats.getStages().getSpecialAttackStage();
                stats.getStages().addSpecialAttackStage(valor);
                int spAtkBase = stats.getSpecialAttack();
                int novoSpAtk = novoAtaqueEspecial(stats.getStages(), spAtkBase);
                stats.setSpecialAttack(novoSpAtk);
                if (novoSpAtk > spAtkBase) {
                    if (anterior + valor > 6) OutputUtils.slowPrint("\nNao foi possivel aumentar o ataque especial de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nAtaque especial de " + target.getName() + " aumentado em " + valor + " estagios!", 50);
                } else if (novoSpAtk < spAtkBase) {
                    if (anterior + valor < -6) OutputUtils.slowPrint("\nNao foi possivel reduzir o ataque especial de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nAtaque especial de " + target.getName() + " reduzido em " + valor + " estagios!", 50);
                }
                break;
            case SPECIAL_DEFENSE:
                anterior = stats.getStages().getSpecialDefenseStage();
                stats.getStages().addSpecialDefenseStage(valor);
                int spDefBase = stats.getSpecialDefense();
                int novaSpDef = novaDefesaEspecial(stats.getStages(), spDefBase);
                stats.setSpecialDefense(novaSpDef);
                if (novaSpDef > spDefBase) {
                    if (anterior + valor > 6) OutputUtils.slowPrint("\nNao foi possivel aumentar a defesa especial de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nDefesa especial de " + target.getName() + " aumentada em " + valor + " estagios!", 50);
                } else if (novaSpDef < spDefBase) {
                    if (anterior + valor < -6) OutputUtils.slowPrint("\nNao foi possivel reduzir a defesa especial de " + target.getName() + "!", 50);
                    else OutputUtils.slowPrint("\nDefesa especial de " + target.getName() + " reduzida em " + valor + " estagios!", 50);
                }
                break;
        }
    }
    public int novoAtaque(StatStages stages, int attack) {
        return (stages.getAttackStage() >= 0) ? 
                (int)(attack * ((2.0 + stages.getAttackStage()) / 2.0)) : 
                (int)(attack * (2.0 / (2.0 - stages.getAttackStage())));
    }
    public int novaDefesa(StatStages stages, int defense) {
        return (stages.getDefenseStage() >= 0) ? 
                (int)(defense * ((2.0 + stages.getDefenseStage()) / 2.0)) : 
                (int)(defense * (2.0 / (2.0 - stages.getDefenseStage())));
    }
    public int novoAtaqueEspecial(StatStages stages, int specialAttack) {
        return (stages.getSpecialAttackStage() >= 0) ? 
                (int)(specialAttack * ((2.0 + stages.getSpecialAttackStage()) / 2.0)) : 
                (int)(specialAttack * (2.0 / (2.0 - stages.getSpecialAttackStage())));
    }
    public int novaDefesaEspecial(StatStages stages, int specialDefense) {
        return (stages.getSpecialDefenseStage() >= 0) ? 
                (int)(specialDefense * ((2.0 + stages.getSpecialDefenseStage()) / 2.0)) : 
                (int)(specialDefense * (2.0 / (2.0 - stages.getSpecialDefenseStage())));
    }
    public double calcularDanoCompleto(Pokemon attacker, Pokemon defender, DamagingMove move) throws InterruptedException {
        double dano = (move.getCategory() == DamagingMoveCategory.PHYSICAL) ? calcularDanoFisico(attacker, defender, move) : calcularDanoEspecial(attacker, defender, move);
        dano += calcularSTAB(attacker, move, dano);
        dano *= calcularEficaciaDeTipo(move.getType(), defender.getTypes());
        if (calcularEficaciaDeTipo(move.getType(), defender.getTypes()) <= 0.5) OutputUtils.slowPrint("\nO golpe " + move.getName() + " nao foi muito eficaz!", 50);
        else if (calcularEficaciaDeTipo(move.getType(), defender.getTypes()) >= 2) OutputUtils.slowPrint("\nO golpe " + move.getName() + " foi super-eficaz!", 50);
        return dano;
    }
    public double calcularEficaciaDeTipo(Typing attacker, List<Typing> defender) {
        double multiplicador = 1.0;
        for (Typing t : defender) {
            multiplicador *= effect.getMultiplicador(attacker, t);
        }
        return multiplicador;
    }
    public double calcularSTAB(Pokemon attacker, Move move, double damage) {
        for (Typing t : attacker.getTypes()) {
            if (move.getType() == t) return damage * 0.5;
        }
        return 0.0;
    }
    public double calcularDanoFisico(Pokemon attacker, Pokemon defender, DamagingMove move) {
        double d1 = ((2 * attacker.getLevel()) / 5) + 2;
        double d2 = (move.getDamage() * attacker.getOwnStats().getAttack()) / defender.getOwnStats().getDefense();
        return ((d1 * d2) / 50) + 2;
    }
    public double calcularDanoEspecial(Pokemon attacker, Pokemon defender, DamagingMove move) {
        double d1 = ((2 * attacker.getLevel()) / 5) + 2;
        double d2 = (move.getDamage() * attacker.getOwnStats().getSpecialAttack()) / defender.getOwnStats().getSpecialDefense();
        return ((d1 * d2) / 50) + 2;
    }
    public Pokemon definirPrimeiro(Pokemon p1, Pokemon p2) {
        return (p1.getOwnStats().getSpeed() >= p2.getOwnStats().getSpeed()) ? p1 : p2;
    }
    public Pokemon definirSegundo(Pokemon p1, Pokemon p2) {
        return (definirPrimeiro(p1, p2) == p1) ? p2 : p1;
    }
}
