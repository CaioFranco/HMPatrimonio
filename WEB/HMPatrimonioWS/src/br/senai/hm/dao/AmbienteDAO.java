package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Solicitacao;
import br.senai.hm.modelo.Usuario;

public interface AmbienteDAO {
	public void inserir(Ambiente a);

	public void alterar(Ambiente a);

	public Ambiente buscarId(Long id);

	public List<Ambiente> buscarTodos();

	public void inativar(Ambiente a);

	public Ambiente buscarUsuario(Usuario u);
	
	
}
