package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Usuario;

public interface UsuarioDAO {
	public void inserir(Usuario u);
	public void alterar(Usuario u);
	public void excluir(Long id);
	public List<Usuario> buscarTodos();
	public Usuario buscarId(Long id);
}
