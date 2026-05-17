package pokedex.builder;

import pokedex.domain.interfaces.MoveCategory;
import pokedex.domain.enums.Typing;
import pokedex.domain.models.DamagingMove;
import pokedex.exception.DadoInvalidoException;

public class DamagingMoveBuilder {
    private DamagingMove move;
    public DamagingMoveBuilder() {
        this.move = new DamagingMove();
    }
    public DamagingMoveBuilder id(int id) {
        move.setId(id);
        return this;
    }
    public DamagingMoveBuilder nome(String nome) {
        move.setName(nome);
        return this;
    }
    public DamagingMoveBuilder tipo(Typing tipo) {
        move.setType(tipo);
        return this;
    }
    public DamagingMoveBuilder categoria(MoveCategory categoria) {
        move.setCategory(categoria);
        return this;
    }
    public DamagingMoveBuilder dano(int dano) throws DadoInvalidoException {
        move.setDamage(dano);
        return this;
    }
    public DamagingMove build() {
        return move;
    }
}
