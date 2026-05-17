package pokedex.domain.models;

import pokedex.domain.enums.StatType;
import pokedex.domain.enums.Target;
import pokedex.domain.enums.Typing;
import pokedex.domain.interfaces.MoveCategory;
import pokedex.domain.records.EffectSet;
import pokedex.exception.DadoInvalidoException;
import pokedex.serializer.MoveSerializer;

public class StatusMove extends Move {
    private int id;
    private String name;
    private Typing type;
    private MoveCategory category;
    private EffectSet effectSet;
    @Override
    public int getId() {
        return id;
    }
    @Override
    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public Typing getType() {
        return type;
    }
    @Override
    public void setType(Typing type) {
        this.type = type;
    }
    @Override
    public MoveCategory getCategory() {
        return category;
    }
    @Override
    public void setCategory(MoveCategory category) {
        this.category = category;
    }
    public EffectSet getEffectSet() {
        return effectSet;
    }
    public void setEffectSet(Target target, StatType stat, int modifier) throws DadoInvalidoException {
        if (modifier < -6 || modifier > 6) throw new DadoInvalidoException("Modificador invalido!");
        this.effectSet = new EffectSet(target, stat, modifier);
    }
    @Override
    public String toFileString() {
        return MoveSerializer.serializeMove(this);
    }
}
