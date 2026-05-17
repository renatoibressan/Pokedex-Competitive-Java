package pokedex.dto;

public record MoveDTO (
    Integer id,
    String name,
    String type,
    String category,
    Integer damage,
    String target,
    String stat,
    Integer modifier
) {}
