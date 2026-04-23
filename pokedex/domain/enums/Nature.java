package pokedex.domain.enums;

import pokedex.exception.DadoInvalidoException;

public enum Nature {
    HARDY,
    LONELY,
    ADAMANT,
    NAUGHTY,
    BRAVE,
    DOCILE,
    BOLD,
    IMPISH,
    LAX,
    RELAXED,
    SERIOUS,
    MODEST,
    MILD,
    RASH,
    QUIET,
    BASHFUL,
    CALM,
    GENTLE,
    CAREFUL,
    SASSY,
    QUIRKY,
    TIMID,
    HASTY,
    JOLLY,
    NAIVE;
    public static Nature fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "hardy": return HARDY;
            case "lonely": return LONELY;
            case "adamant": return ADAMANT;
            case "naughty": return NAUGHTY;
            case "brave": return BRAVE;
            case "docile": return DOCILE;
            case "bold": return BOLD;
            case "impish": return IMPISH;
            case "lax": return LAX;
            case "relaxed": return RELAXED;
            case "serious": return SERIOUS;
            case "modest": return MODEST;
            case "mild": return MILD;
            case "rash": return RASH;
            case "quiet": return QUIET;
            case "bashful": return BASHFUL;
            case "calm": return CALM;
            case "gentle": return GENTLE;
            case "careful": return CAREFUL;
            case "sassy": return SASSY;
            case "quirky": return QUIRKY;
            case "timid": return TIMID;
            case "hasty": return HASTY;
            case "jolly": return JOLLY;
            case "naive": return NAIVE;
            default: throw new DadoInvalidoException("Nature invalida!");
        }
    }
}
