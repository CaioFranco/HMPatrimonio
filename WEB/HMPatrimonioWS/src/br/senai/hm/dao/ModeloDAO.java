package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Modelo;

public interface ModeloDAO {
	public void inserir(Modelo m);
	public void alterar (Modelo m);
	public void inativar(Modelo m);
	List<Modelo> buscarTodos();
	Modelo buscarId(Long id);
	
}
