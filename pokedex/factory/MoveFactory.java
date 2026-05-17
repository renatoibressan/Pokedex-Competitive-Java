package pokedex.factory;

import pokedex.builder.DamagingMoveBuilder;
import pokedex.builder.StatusMoveBuilder;
import pokedex.domain.enums.StatType;
import pokedex.domain.enums.Target;
import pokedex.domain.enums.Typing;
import pokedex.domain.interfaces.MoveCategory;
import pokedex.domain.models.Move;
import pokedex.dto.MoveDTO;
import pokedex.exception.DadoInvalidoException;

public class MoveFactory {
    public Move create(MoveDTO dto) throws DadoInvalidoException {
        if (dto.category().equals("STATUS")) {
            return new StatusMoveBuilder()
                                        .id(dto.id())
                                        .nome(dto.name())
                                        .tipo(Typing.fromString(dto.type()))
                                        .categoria(MoveCategory.fromString(dto.category()))
                                        .conjuntoEfeito(Target.valueOf(dto.target()), 
                                                        StatType.valueOf(dto.stat()), 
                                                        dto.modifier())
                                        .build();
        }
        return new DamagingMoveBuilder()
                                    .id(dto.id())
                                    .nome(dto.name())
                                    .tipo(Typing.fromString(dto.type()))
                                    .categoria(MoveCategory.fromString(dto.category()))
                                    .dano(dto.damage())
                                    .build();
    }
}
