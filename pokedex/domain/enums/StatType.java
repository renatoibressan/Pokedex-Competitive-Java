package pokedex.domain.enums;

import pokedex.exception.DadoInvalidoException;

public enum StatType {
    ATTACK,
    DEFENSE,
    SPECIAL_ATTACK,
    SPECIAL_DEFENSE,
    SPEED;
    public static StatType fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "ataque": case "attack": return ATTACK;
            case "defesa": case "defense": return DEFENSE;
            case "ataque especial": case "special attack": return SPECIAL_ATTACK;
            case "defesa especial": case "special defense": return SPECIAL_DEFENSE;
            case "velocidade": case "speed": return SPEED;
            default: throw new DadoInvalidoException("Modificador invalido!");
        }
    }
}
