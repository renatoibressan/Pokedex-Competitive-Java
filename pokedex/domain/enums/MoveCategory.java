package pokedex.domain.enums;

import pokedex.exception.DadoInvalidoException;

public enum MoveCategory {
    PHYSICAL,
    SPECIAL;
    public static MoveCategory fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "fisico": case "physical": return PHYSICAL;
            case "especial": case "special": return SPECIAL;
            default: throw new DadoInvalidoException("Categoria invalida!");
        }
    }
}
