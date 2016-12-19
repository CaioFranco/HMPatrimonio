package br.senai.hm.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import application.Main;
import br.senai.hm.modelo.Solicitacao;

public class SolicitacaoDAO {
	EntityManager manager;
	
	public SolicitacaoDAO() {
		manager = Main.getManager();
	}
	
	public List<Solicitacao> buscarTodos() {
		manager.getTransaction().begin();
		TypedQuery<Solicitacao> query = manager.createQuery("SELECT s FROM Solicitacao s", Solicitacao.class);
		manager.getTransaction().commit();
		return query.getResultList();
	}
	
	public Solicitacao buscarId(Long id) {
		return manager.find(Solicitacao.class, id);
	}
	
}
