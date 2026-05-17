package pokedex.domain.models;

public class StatStages {
    private int attackStage;
    private int defenseStage;
    private int specialAttackStage;
    private int specialDefenseStage;
    public StatStages() {
        this.attackStage = 0;
        this.defenseStage = 0;
        this.specialAttackStage = 0;
        this.specialDefenseStage = 0;
    }
    public int getAttackStage() {
        return attackStage;
    }
    public void addAttackStage(int value) {
        this.attackStage = Math.max(-6, Math.min(6, this.attackStage + value)); 
    }
    public int getDefenseStage() {
        return defenseStage;
    }
    public void addDefenseStage(int value) {
        this.defenseStage = Math.max(-6, Math.min(6, this.defenseStage + value)); 
    }
    public int getSpecialAttackStage() {
        return specialAttackStage;
    }
    public void addSpecialAttackStage(int value) {
        this.specialAttackStage = Math.max(-6, Math.min(6, this.specialAttackStage + value)); 
    }
    public int getSpecialDefenseStage() {
        return specialDefenseStage;
    }
    public void addSpecialDefenseStage(int value) {
        this.specialDefenseStage = Math.max(-6, Math.min(6, this.specialDefenseStage + value)); 
    }
}
