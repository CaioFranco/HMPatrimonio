package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Transferencia;

public interface TransferenciaDAO {
	public void transferir(Transferencia t);
	public void alterar (Transferencia t);
	public Transferencia buscarId(Long id);
	public List<Transferencia> buscarTodos();
}
