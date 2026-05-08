package pokedex.repository.interfaces;

import java.util.List;

import pokedex.domain.enums.Typing;

public interface ObjectRepository<T> {
    public boolean existe(String nome);
    public void salvar(T valor);
    public List<T> listar();
    public T buscarPorNome(String nome);
    public List<T> buscarPorTipo(Typing tipo);
    public void remover(int id);
}
