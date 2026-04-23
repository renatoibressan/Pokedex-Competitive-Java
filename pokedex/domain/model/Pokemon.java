package pokedex.domain.model;

import java.util.List;

import pokedex.domain.enums.Type;

public class Pokemon {
    
    private int id;
    private String name;
    private List<Type> types;
    private int hp;
    private int atk;
    private int def;
    private int spAtk;
    private int spDef;
    private int speed;

    private List<Move> moves;
    private EvolutionChain evolutionChain;

}
