package pokedex.dto;

public record PokemonDTO(
    Integer id, 
    String name, 
    String types, 
    String stats
) {}
