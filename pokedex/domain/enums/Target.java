package pokedex.domain.enums;

import pokedex.exception.DadoInvalidoException;

public enum Target {
    SELF,
    FOE;
    public static Target fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "proprio": case "self": return SELF;
            case "adversario": case "oponente": case "foe": return FOE;
            default: throw new DadoInvalidoException("Alvo invalido!");
        }
    }
}
