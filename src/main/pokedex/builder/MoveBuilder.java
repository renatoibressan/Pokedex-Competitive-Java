package src.main.pokedex.builder;

import src.main.pokedex.domain.enums.MoveCategory;
import src.main.pokedex.domain.enums.Typing;
import src.main.pokedex.domain.model.Move;
import src.main.pokedex.exception.DadoInvalidoException;

public class MoveBuilder {
    private Move move;
    public MoveBuilder() {
        this.move = new Move();
    }
    public MoveBuilder id(int id) {
        move.setId(id);
        return this;
    }
    public MoveBuilder nome(String nome) {
        move.setName(nome);
        return this;
    }
    public MoveBuilder tipo(Typing tipo) {
        move.setType(tipo);
        return this;
    }
    public MoveBuilder dano(int dano) throws DadoInvalidoException {
        move.setDamage(dano);
        return this;
    }
    public MoveBuilder categoria(MoveCategory categoria) {
        move.setCategory(categoria);
        return this;
    }
    public Move build() {
        return move;
    }
}
