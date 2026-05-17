package pokedex.domain.records;

import pokedex.domain.enums.StatType;
import pokedex.domain.enums.Target;

public record EffectSet(Target target, StatType stat, int modifier) {}
