package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Solicitacao;
import br.senai.hm.modelo.Usuario;

public interface SolicitacaoDAO {
	public void realizar(Solicitacao s);

	public void responder(Solicitacao s);

	public List<Solicitacao> buscarTodos();

	public Solicitacao buscarId(Long id);
	
	public List<Patrimonio> buscar_Patrimonios() ;
	
	public void rejeitar(Solicitacao s);
	
	public void permitir(Solicitacao s);
	
	public List<Solicitacao> buscar_destino(Long idAmbiente);
	
	public List<Solicitacao> buscar_resposta(boolean b );
	
	public List<Solicitacao> buscar_SolitacaoUsuario(Usuario u);
	
	public List<Solicitacao> buscar_SolicitacaoRespondida(Usuario u);
}

