package pokedex.domain.enums;

import pokedex.exception.DadoInvalidoException;

public enum Typing {
    GRASS,
    FIRE,
    WATER,
    NORMAL,
    ELECTRIC,
    PSYCHIC,
    FIGHTING,
    ROCK,
    GROUND,
    FLYING,
    BUG,
    POISON,
    DARK,
    GHOST,
    ICE,
    STEEL,
    DRAGON,
    FAIRY;
    public static Typing fromString(String valor) throws DadoInvalidoException {
        switch (valor.toLowerCase()) {
            case "normal": return NORMAL;
            case "grama": case "grass": return GRASS;
            case "fogo": case "fire": return FIRE;
            case "agua": case "water": return WATER;
            case "eletrico": case "electric": return ELECTRIC;
            case "psiquico": case "psychic": return PSYCHIC;
            case "lutador": case "fighting": return FIGHTING;
            case "pedra": case "rock": return ROCK;
            case "terrestre": case "ground": return GROUND;
            case "voador": case "flying": return FLYING;
            case "inseto": case "bug": return BUG;
            case "veneno": case "poison": return POISON;
            case "sombrio": case "dark": return DARK;
            case "fantasma": case "ghost": return GHOST;
            case "gelo": case "ice": return ICE;
            case "aco": case "steel": return STEEL;
            case "dragao": case "dragon": return DRAGON;
            case "fada": case "fairy": return FAIRY;
            default: throw new DadoInvalidoException("Tipo invalido!");
        }
    }
}
