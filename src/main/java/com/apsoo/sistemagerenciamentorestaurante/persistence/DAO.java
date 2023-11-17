package com.apsoo.sistemagerenciamentorestaurante.persistence;

import java.util.List;

public interface DAO<T, S> {
    T inserir(T objeto);
    void atualizar(T objeto);
    T recuperar(S objetoID);
    List<T> recuperarTodos();
    void remover(S objetoID);
}
