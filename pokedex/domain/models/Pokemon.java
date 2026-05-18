package pokedex.domain.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import pokedex.domain.enums.Nature;
import pokedex.domain.enums.Typing;
import pokedex.exception.DadoInvalidoException;
import pokedex.serializer.PokemonSerializer;

public class Pokemon {
    private int id;
    private String name;
    private List<Typing> types;
    private Stats baseStats;
    private Stats ownStats;
    private Nature nature;
    private int level;
    private List<Move> moves;
    public Pokemon() {
        this.types = new ArrayList<>();
        this.baseStats = new Stats();
        this.ownStats = new Stats();
        this.moves = new ArrayList<>();
    }
    public Pokemon(Pokemon original) {
        this.id = original.id;
        this.name = original.name;
        this.types = original.types;
        this.baseStats = new Stats(original.baseStats);
        this.ownStats = new Stats(original.ownStats);
        this.nature = original.nature;
        this.level = original.level;
        this.moves = original.moves;
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
        if (types == null || types.isEmpty()) throw new DadoInvalidoException("O Pokemon deve ter pelo menos 1 tipo!");
        if (types.size() > 2) throw new DadoInvalidoException("O Pokemon nao pode ter mais de 2 tipos!");
        if (new HashSet<>(types).size() != types.size()) throw new DadoInvalidoException("Tipos duplicados nao sao permitidos!");
        this.types = types;
    }
    public Stats getBaseStats() {
        return baseStats;
    }
    public void setBaseStats(Stats baseStats) {
        this.baseStats = baseStats;
    }
    public Stats getOwnStats() {
        return ownStats;
    }
    public void setOwnStats(int level, Nature nature) throws DadoInvalidoException {
        ownStats = baseStats.computeNewStats(level, nature);
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
        if (level < 1 || level > 100) throw new DadoInvalidoException("O Pokemon deve ser de nivel 1 a 100!");
        this.level = level;
    }
    public List<Move> getMoves() {
        return moves;
    }
    public void setMoves(List<Move> moves) throws DadoInvalidoException {
        if (moves == null || moves.isEmpty()) throw new DadoInvalidoException("O Pokemon deve ter pelo menos 1 golpe!");
        if (moves.size() > 4) throw new DadoInvalidoException("O Pokemon nao pode ter mais de 4 golpes!");
        if (new HashSet<>(moves).size() != moves.size()) throw new DadoInvalidoException("Golpes duplicados nao sao permitidos!");
        this.moves = moves;
    }
    public int getBST() {
        return baseStats.baseStatTotal();
    }
    public int getStatFromString(String value) throws DadoInvalidoException {
        return baseStats.statFromString(value);
    }
    public String toFileString() {
        return PokemonSerializer.serializePokemon(this);
    }
}
