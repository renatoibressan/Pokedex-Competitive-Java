package pokedex.domain.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import pokedex.builder.StatsBuilder;
import pokedex.domain.enums.Nature;
import pokedex.domain.enums.Typing;
import pokedex.exception.DadoInvalidoException;
import pokedex.serializer.PokemonSerializer;

public class Pokemon {
    private int id;
    private String name;
    private List<Typing> types;
    private Stats baseStats;
    private Stats stats;
    private Nature nature;
    private int level;
    private List<Move> moves;
    public Pokemon() {
        this.types = new ArrayList<>();
        this.baseStats = new Stats();
        this.stats = new Stats();
        this.moves = new ArrayList<>();
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Typing> getTypes() {
        return Collections.unmodifiableList(types);
    }
    public void setTypes(List<Typing> types) throws DadoInvalidoException {
        if (types == null || types.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 tipo!");
        if (types.size() > 2) throw new DadoInvalidoException("Pokemon nao pode ter mais de 2 tipos!");
        if (new HashSet<>(types).size() != types.size()) throw new DadoInvalidoException("Tipos duplicados nao sao permitidos!");
        this.types = types;
    }
    public Stats getBaseStats() {
        return baseStats;
    }
    public void setBaseStats(Stats baseStats) {
        this.baseStats = baseStats;
    }
    public Stats getStats() {
        return stats;
    }
    public void setStats(Stats baseStats, Nature nature, int nivel) throws DadoInvalidoException {
        int hpBase = baseStats.getHp();
        int atkBase = baseStats.getAttack();
        int defBase = baseStats.getDefense();
        int spAtkBase = baseStats.getSpecialAttack();
        int spDefBase = baseStats.getSpecialDefense();
        int speedBase = baseStats.getSpeed();
        int hp = (int) Math.floor((((hpBase * 2) + 31) * nivel) / 100) + nivel + 10;
        int atk = (int) Math.floor((((atkBase * 2) + 31) * nivel) / 100) + 5;
        int def = (int) Math.floor((((defBase * 2) + 31) * nivel) / 100) + 5;
        int spAtk = (int) Math.floor((((spAtkBase * 2) + 31) * nivel) / 100) + 5;
        int spDef = (int) Math.floor((((spDefBase * 2) + 31) * nivel) / 100) + 5;
        int speed = (int) Math.floor((((speedBase * 2) + 31) * nivel) / 100) + 5;
        switch (nature) {
            case HARDY: case DOCILE: case SERIOUS: case BASHFUL: case QUIRKY: break;
            case LONELY: case ADAMANT: case NAUGHTY: case BRAVE: atk += atk / 10; break;
            case BOLD: case IMPISH: case LAX: case RELAXED: def += def / 10; break;
            case MODEST: case MILD: case RASH: case QUIET: spAtk += spAtk / 10; break;
            case CALM: case GENTLE: case CAREFUL: case SASSY: spDef += spDef / 10; break;
            case TIMID: case HASTY: case JOLLY: case NAIVE: speed += speed / 10; break;
        }
        switch (nature) {
            case HARDY: case DOCILE: case SERIOUS: case BASHFUL: case QUIRKY: break;
            case BOLD: case MODEST: case CALM: case TIMID: atk -= atk / 10; break;
            case LONELY: case MILD: case GENTLE: case HASTY: def -= def / 10; break;
            case ADAMANT: case IMPISH: case CAREFUL: case JOLLY: spAtk -= spAtk / 10; break;
            case NAUGHTY: case LAX: case RASH: case NAIVE: spDef -= spDef / 10; break;
            case BRAVE: case RELAXED: case QUIET: case SASSY: speed -= speed / 10; break;
        }
        stats = new StatsBuilder()
                    .hp(hp)
                    .ataque(atk)
                    .defesa(def)
                    .ataqueEspecial(spAtk)
                    .defesaEspecial(spDef)
                    .velocidade(speed)
                    .build();
    }
    public Nature getNature() {
        return nature;
    }
    public void setNature(Nature nature) {
        this.nature = nature;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) throws DadoInvalidoException {
        if (level < 1 || level > 100) throw new DadoInvalidoException("Pokemon deve ser de nivel 1 a 100!");
        this.level = level;
    }
    public List<Move> getMoves() {
        return moves;
    }
    public void setMoves(List<Move> moves) throws DadoInvalidoException {
        if (moves == null || moves.isEmpty()) throw new DadoInvalidoException("Pokemon deve ter pelo menos 1 golpe!");
        if (moves.size() > 4) throw new DadoInvalidoException("Pokemon nao pode ter mais de 4 golpes!");
        if (new HashSet<>(moves).size() != moves.size()) throw new DadoInvalidoException("Golpes duplicados nao sao permitidos!");
        this.moves = moves;
    }
    public int getBST() {
        return baseStats.baseStatTotal();
    }
    public int statFromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "hp": return baseStats.getHp();
            case "ataque": case "attack": return baseStats.getAttack();
            case "defesa": case "defense": return baseStats.getDefense();
            case "ataque especial": case "special attack": return baseStats.getSpecialAttack();
            case "defesa especial": case "special defense": return baseStats.getSpecialDefense();
            case "velocidade": case "speed": return baseStats.getSpeed();
            default: throw new DadoInvalidoException("Stat inexistente!");
        }
    }
    public String toFileString() {
        return PokemonSerializer.serializePokemon(this);
    }
}
