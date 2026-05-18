package pokedex.serializer;

import java.util.stream.Collectors;

import pokedex.domain.models.Pokemon;
import pokedex.domain.models.Team;

public class TeamSerializer {
    public static String serializeTeam(Team team) {
        return String.join(",", 
                String.valueOf(team.getId()), 
                team.getName(), 
                serializePokemons(team));
    }
    private static String serializePokemons(Team team) {
        return team
                .getPokemons()
                .stream() 
                .map(Pokemon::getName) 
                .collect(Collectors.joining("|"));
    }
}
