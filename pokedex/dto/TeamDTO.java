package pokedex.dto;

public record TeamDTO(
    Integer id, 
    String name, 
    String trainer, 
    String pokemons
) {}
