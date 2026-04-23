package pokedex.exception;

public class PokemonNaoEncontradoException extends Exception {
    public PokemonNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
