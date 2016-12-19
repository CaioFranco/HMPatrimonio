package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Movimentacao;
import br.senai.hm.modelo.Solicitacao;


public interface MovimentacaoDAO {
	List<Movimentacao> buscarTodos();
	 Movimentacao buscarId(Long id);
	public void inserir(Movimentacao mv);
	public List<Movimentacao> buscar_destino(Long idAmbiente);
}
