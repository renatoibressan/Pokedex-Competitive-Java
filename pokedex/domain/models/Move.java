package pokedex.domain.models;

import pokedex.domain.enums.Typing;
import pokedex.domain.interfaces.MoveCategory;

public abstract class Move {
    private int id;
    private String name;
    private Typing type;
    private MoveCategory category;
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Typing getType() { return type; }
    public void setType(Typing type) { this.type = type; }
    public MoveCategory getCategory() { return category; }
    public void setCategory(MoveCategory category) { this.category = category; }
    public String toFileString() { return String.join(";", nullable(id), nullable(name), nullable(type), nullable(category)); }
    protected String nullable(Object valor) { return valor == null ? "" : valor.toString(); }
}
