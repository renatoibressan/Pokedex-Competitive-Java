package pokedex.serializer;

import pokedex.domain.models.DamagingMove;
import pokedex.domain.models.Move;
import pokedex.domain.models.StatusMove;

public class MoveSerializer {
    public static String serializeMove(Move move) {
        if (move instanceof DamagingMove damagingMove) return serializeDamagingMove(damagingMove);
        return serializeStatusMove((StatusMove) move);
    }
    public static String serializeDamagingMove(DamagingMove move) {
        return String.join(",", 
                nullable(move.getId()), 
                nullable(move.getName()), 
                nullable(move.getType()), 
                nullable(move.getCategory()), 
                nullable(move.getDamage()),
                "",
                "",
                "");
    }
    public static String serializeStatusMove(StatusMove move) {
        return String.join(",", nullable(move.getId()), 
                nullable(move.getName()), 
                nullable(move.getType()), 
                nullable(move.getCategory()),
                "",
                nullable(move.getEffectSet().target()),
                nullable(move.getEffectSet().stat()),
                nullable(move.getEffectSet().modifier()));
    }
    public static String nullable(Object valor) {
        return valor == null ? "" : valor.toString();
    }
}
