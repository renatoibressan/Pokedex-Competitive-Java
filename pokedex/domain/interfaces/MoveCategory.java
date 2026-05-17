package pokedex.domain.interfaces;

import pokedex.domain.enums.DamagingMoveCategory;
import pokedex.domain.enums.StatusMoveCategory;
import pokedex.exception.DadoInvalidoException;

public interface MoveCategory {
    public static MoveCategory fromString(String valor) throws DadoInvalidoException {
        for (DamagingMoveCategory category : DamagingMoveCategory.values()) if (category.name().equalsIgnoreCase(valor)) return category;
        for (StatusMoveCategory category : StatusMoveCategory.values()) if (category.name().equalsIgnoreCase(valor)) return category;
        throw new DadoInvalidoException("Categoria invalida!");
    }
}
