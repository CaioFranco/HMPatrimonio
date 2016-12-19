package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Conferencia;

public interface ConferenciaDAO {
	public void inserir(Conferencia c);
	public void alterar(Conferencia c);
	public Conferencia buscarId(Long id);
	public List<Conferencia> buscarTodos();
}
