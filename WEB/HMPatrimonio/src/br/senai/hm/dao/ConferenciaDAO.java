package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Usuario;

public interface ConferenciaDAO {
	public void inserir(Conferencia c);
	public Conferencia buscarId(Long id);
	public List<Conferencia> buscarTodos();
	public List<Conferencia> buscar_ConferenciaUsuario(Usuario u);
	//public Conferencia buscar_status();
	
	
	
}
