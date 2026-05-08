package pokedex.builder;

import pokedex.domain.model.Stats;
import pokedex.exception.DadoInvalidoException;

public class StatsBuilder {
    private Stats stats;
    public StatsBuilder() {
        this.stats = new Stats();
    }
    public StatsBuilder hp(int hp) throws DadoInvalidoException {
        stats.setHp(hp);
        return this;
    }
    public StatsBuilder ataque(int ataque) throws DadoInvalidoException {
        stats.setAttack(ataque);
        return this;
    }
    public StatsBuilder defesa(int defesa) throws DadoInvalidoException {
        stats.setDefense(defesa);
        return this;
    }
    public StatsBuilder ataqueEspecial(int ataqueEspecial) throws DadoInvalidoException {
        stats.setSpecialAttack(ataqueEspecial);
        return this;
    }
    public StatsBuilder defesaEspecial(int defesaEspecial) throws DadoInvalidoException {
        stats.setSpecialDefense(defesaEspecial);
        return this;
    }
    public StatsBuilder velocidade(int velocidade) throws DadoInvalidoException {
        stats.setSpeed(velocidade);
        return this;
    }
    public Stats build() {
        return stats;
    }
}
