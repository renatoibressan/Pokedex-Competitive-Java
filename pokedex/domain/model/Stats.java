package pokedex.domain.model;

import pokedex.exception.DadoInvalidoException;

public class Stats {
    private int hp;
    private int attack;
    private int defense;
    private int specialAttack;
    private int specialDefense;
    private int speed;
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
    public int baseStatTotal() {
        return hp + attack + defense + specialAttack + specialDefense + speed;
    }
}
