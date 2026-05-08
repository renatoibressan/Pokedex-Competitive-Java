package pokedex.serializer;

import pokedex.domain.model.Move;

public class MoveSerializer {
    public static String serializeMove(Move move) {
        return String.join(";", 
                String.valueOf(move.getId()), 
                move.getName(), 
                String.valueOf(move.getType()), 
                String.valueOf(move.getDamage()), 
                String.valueOf(move.getCategory()));
    }
}
