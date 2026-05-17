package pokedex.domain.models;

import pokedex.builder.StatsBuilder;
import pokedex.domain.enums.Nature;
import pokedex.exception.DadoInvalidoException;

public class Stats {
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
    private StatStages stages;
    public Stats() {
        this.stages = new StatStages();
    }
    public Stats(Stats original) {
        this.hp = original.hp;
        this.attack = original.attack;
        this.defense = original.defense;
        this.specialAttack = original.specialAttack;
        this.specialDefense = original.specialDefense;
        this.speed = original.speed;
        this.stages = new StatStages();
    }
    public int getHp() {
        return hp;
    }
    public void setHp(int hp) throws DadoInvalidoException {
        if (hp < 1) throw new DadoInvalidoException("Stat de hp invalido!");
        this.hp = hp;
    }
    public int getAttack() {
        return attack;
    }
    public void setAttack(int atk) throws DadoInvalidoException {
        if (atk < 1) throw new DadoInvalidoException("Stat de ataque invalido!");
        this.attack = atk;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int def) throws DadoInvalidoException {
        if (def < 1) throw new DadoInvalidoException("Stat de defesa invalido!");
        this.defense = def;
    }
    public int getSpecialAttack() {
        return specialAttack;
    }
    public void setSpecialAttack(int spAtk) throws DadoInvalidoException {
        if (spAtk < 1) throw new DadoInvalidoException("Stat de ataque especial invalido!");
        this.specialAttack = spAtk;
    }
    public int getSpecialDefense() {
        return specialDefense;
    }
    public void setSpecialDefense(int spDef) throws DadoInvalidoException {
        if (spDef < 1) throw new DadoInvalidoException("Stat de defesa especial invalido!");
        this.specialDefense = spDef;
    }
    public int getSpeed() {
        return speed;
    }
    public void setSpeed(int speed) throws DadoInvalidoException {
        if (speed < 1) throw new DadoInvalidoException("Stat de velocidade invalido!");
        this.speed = speed;
    }
    public StatStages getStages() {
        return stages;
    }
    public int baseStatTotal() {
        return hp + attack + defense + specialAttack + specialDefense + speed;
    }
    public Stats computeNewStats(int level, Nature nature) throws DadoInvalidoException {
        int newHp = (int) Math.floor((((hp * 2) + 31) * level) / 100) + level + 10;
        int newAtk = (int) Math.floor((((attack * 2) + 31) * level) / 100) + 5;
        int newDef = (int) Math.floor((((defense * 2) + 31) * level) / 100) + 5;
        int newSpAtk = (int) Math.floor((((specialAttack * 2) + 31) * level) / 100) + 5;
        int newSpDef = (int) Math.floor((((specialDefense * 2) + 31) * level) / 100) + 5;
        int newSpeed = (int) Math.floor((((speed * 2) + 31) * level) / 100) + 5;
        switch (nature) {
            case HARDY: case DOCILE: case SERIOUS: case BASHFUL: case QUIRKY: break;
            case LONELY: case ADAMANT: case NAUGHTY: case BRAVE: newAtk += newAtk / 10; break;
            case BOLD: case IMPISH: case LAX: case RELAXED: newDef += newDef / 10; break;
            case MODEST: case MILD: case RASH: case QUIET: newSpAtk += newSpAtk / 10; break;
            case CALM: case GENTLE: case CAREFUL: case SASSY: newSpDef += newSpDef / 10; break;
            case TIMID: case HASTY: case JOLLY: case NAIVE: newSpeed += newSpeed / 10; break;
        }
        switch (nature) {
            case HARDY: case DOCILE: case SERIOUS: case BASHFUL: case QUIRKY: break;
            case BOLD: case MODEST: case CALM: case TIMID: newAtk -= newAtk / 10; break;
            case LONELY: case MILD: case GENTLE: case HASTY: newDef -= newDef / 10; break;
            case ADAMANT: case IMPISH: case CAREFUL: case JOLLY: newSpAtk -= newSpAtk / 10; break;
            case NAUGHTY: case LAX: case RASH: case NAIVE: newSpDef -= newSpDef / 10; break;
            case BRAVE: case RELAXED: case QUIET: case SASSY: newSpeed -= newSpeed / 10; break;
        }
        return new StatsBuilder()
        .hp(newHp)
        .ataque(newAtk)
        .defesa(newDef)
        .ataqueEspecial(newSpAtk)
        .defesaEspecial(newSpDef)
        .velocidade(newSpeed)
        .build();
    }
    public int statFromString(String value) throws DadoInvalidoException {
        switch (value.toLowerCase()) {
            case "hp": return hp;
            case "ataque": case "attack": return attack;
            case "defesa": case "defense": return defense;
            case "ataque especial": case "special attack": return specialAttack;
            case "defesa especial": case "special defense": return specialDefense;
            case "velocidade": case "speed": return speed;
            default: throw new DadoInvalidoException("Stat inexistente!");
        }
    }
}
