package pokedex.builder;

import pokedex.domain.interfaces.MoveCategory;
import pokedex.domain.enums.StatType;
import pokedex.domain.enums.Target;
import pokedex.domain.enums.Typing;
import pokedex.domain.models.StatusMove;
import pokedex.exception.DadoInvalidoException;

public class StatusMoveBuilder {
    private StatusMove move;
    public StatusMoveBuilder() {
        this.move = new StatusMove();
    }
    public StatusMoveBuilder id(int id) {
        move.setId(id);
        return this;
    }
    public StatusMoveBuilder nome(String nome) {
        move.setName(nome);
        return this;
    }
    public StatusMoveBuilder tipo(Typing tipo) {
        move.setType(tipo);
        return this;
    }
    public StatusMoveBuilder categoria(MoveCategory categoria) {
        move.setCategory(categoria);
        return this;
    }
    public StatusMoveBuilder conjuntoEfeito(Target alvo, StatType atributo, int modificador) throws DadoInvalidoException {
        move.setEffectSet(alvo, atributo, modificador);
        return this;
    }
    public StatusMove build() {
        return move;
    }
}
