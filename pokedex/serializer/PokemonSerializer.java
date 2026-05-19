package pokedex.serializer;

import java.util.stream.Collectors;

import pokedex.domain.enums.Typing;
import pokedex.domain.models.Pokemon;

public class PokemonSerializer {
    public static String serializePokemon(Pokemon pokemon) {
        return String.join(",", 
                String.valueOf(pokemon.getId()), 
                pokemon.getName(), 
                serializeTypes(pokemon), 
                serializeStats(pokemon),
                String.valueOf(pokemon.getGeneration()));
    }
    private static String serializeTypes(Pokemon pokemon) {
        return pokemon
                .getTypes() 
                .stream() 
                .map(Typing::name) 
                .collect(Collectors.joining("|"));
    }
    private static String serializeStats(Pokemon pokemon) {
        return String.join("/", 
                String.valueOf(pokemon.getBaseStats().getHp()), 
                String.valueOf(pokemon.getBaseStats().getAttack()), 
                String.valueOf(pokemon.getBaseStats().getDefense()), 
                String.valueOf(pokemon.getBaseStats().getSpecialAttack()), 
                String.valueOf(pokemon.getBaseStats().getSpecialDefense()), 
                String.valueOf(pokemon.getBaseStats().getSpeed()));
    }
}
