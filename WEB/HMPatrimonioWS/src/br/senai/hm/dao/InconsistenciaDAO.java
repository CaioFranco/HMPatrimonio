package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Inconsistencia;

public interface InconsistenciaDAO {
	public void inserir(Inconsistencia i);
	public void alterar (Inconsistencia i);
	public List<Inconsistencia> buscarTodos();
	public Inconsistencia  buscarId(Long id);


}
