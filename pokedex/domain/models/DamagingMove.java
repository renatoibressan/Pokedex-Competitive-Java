package pokedex.domain.models;

import pokedex.domain.enums.Typing;
import pokedex.domain.interfaces.MoveCategory;
import pokedex.exception.DadoInvalidoException;
import pokedex.serializer.MoveSerializer;

public class DamagingMove extends Move {
    private int id;
    private String name;
    private Typing type;
    private MoveCategory category;
    private int damage;
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
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) throws DadoInvalidoException {
        if (damage < 0) throw new DadoInvalidoException("Dano base invalido!");
        this.damage = damage;
    }
    @Override
    public String toFileString() {
        return MoveSerializer.serializeMove(this);
    }
}
