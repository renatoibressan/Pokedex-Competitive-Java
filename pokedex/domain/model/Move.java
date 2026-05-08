package pokedex.domain.model;

import pokedex.domain.enums.MoveCategory;
import pokedex.domain.enums.Typing;
import pokedex.exception.DadoInvalidoException;
import pokedex.serializer.MoveSerializer;

public class Move {
    private int id;
    private String name;
    private Typing type;
    private int damage;
    private MoveCategory category;
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
    public Typing getType() {
        return type;
    }
    public void setType(Typing type) {
        this.type = type;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) throws DadoInvalidoException {
        if (damage < 0 || damage % 5 != 0) throw new DadoInvalidoException("Dano base invalido!");
        this.damage = damage;
    }
    public MoveCategory getCategory() {
        return category;
    }
    public void setCategory(MoveCategory category) {
        this.category = category;
    }
    public String toFileString() {
        return MoveSerializer.serializeMove(this);
    }
}
