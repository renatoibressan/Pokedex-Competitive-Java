package pokedex.repository.interfaces;

import java.util.Collections;
import java.util.List;

import pokedex.domain.enums.Typing;

public interface ObjectRepository<T, V> {
    public boolean existe(String nome);
    public void salvar(T valor);
    public List<T> listar();
    public T buscarPorNome(String nome);
    default List<T> listarGrupo(V valor) { return Collections.emptyList(); }
    default List<T> buscarPorTipo(Typing tipo) { return Collections.emptyList(); }
    public void remover(int id);
    public void inserirLista(List<T> lista);
    public void excluirTodos();
    public int contarQuantidade();
}
