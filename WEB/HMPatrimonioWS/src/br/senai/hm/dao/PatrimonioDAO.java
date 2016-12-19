package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Situacao;

public interface PatrimonioDAO{
	public void inserir(Patrimonio p);
	public void alterar (Patrimonio p);
	public void inativar(Patrimonio p);
	public List<Patrimonio> buscarTodos();
	public Patrimonio buscarId(Long id);
	public List<Patrimonio> buscar_status(boolean s);
	public List<Patrimonio> buscar_descricao(String descricao);
	public List<Patrimonio> buscar_modelo(String componente);
	public List<Patrimonio> buscar_ambiente(Long id);
	public List<Patrimonio> buscar_situacao(Situacao s);
	
}
