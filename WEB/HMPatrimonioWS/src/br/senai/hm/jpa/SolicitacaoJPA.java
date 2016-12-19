package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.SolicitacaoDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Solicitacao;
import br.senai.hm.modelo.Usuario;

@Repository
public class SolicitacaoJPA implements SolicitacaoDAO {
	@PersistenceContext
	EntityManager manager;

	@Override
	public void realizar(Solicitacao s) {
		// manager.getTransaction().begin();
		manager.persist(s);
		System.out.println("ok");
		// manager.getTransaction().commit();
	}

	@Override
	public void responder(Solicitacao s) {
		manager.getTransaction().begin();
		manager.merge(s);
		manager.getTransaction().commit();

	}

	@Override
	public List<Solicitacao> buscarTodos() {
		// manager.getTransaction().begin();
		TypedQuery<Solicitacao> query = manager.createQuery(
				"SELECT s FROM Solicitacao s", Solicitacao.class);
		// manager.getTransaction().commit();
		return query.getResultList();

	}

	@Override
	public Solicitacao buscarId(Long id) {
		return manager.find(Solicitacao.class, id);
	}

	public List<Patrimonio> buscar_Patrimonios() {
		TypedQuery<Patrimonio> query = manager.createQuery(
				"SELECT p FROM Patrimonio p", Patrimonio.class);
		return query.getResultList();
	}

	public void rejeitar(Solicitacao s) {
		Solicitacao ss = manager.find(Solicitacao.class, s.getId());
		ss.setResposta(false);
		manager.merge(ss);

	}

	public void permitir(Solicitacao s) {
		Solicitacao ss = manager.find(Solicitacao.class, s.getId());
		ss.setResposta(true);
		manager.merge(ss);

	}
	
	public List<Solicitacao> buscar_destino(Long id) {
		Ambiente a = manager.find(Ambiente.class, id);
		TypedQuery<Solicitacao> query = manager.createQuery("SELECT s FROM Solicitacao s WHERE s.ambiente_destino = :a", Solicitacao.class);
		query.setParameter("a",a);	
		if(query.getResultList().size() > 0 ){
			return query.getResultList();		
		}
		return null;
	}

	public List<Solicitacao> buscar_resposta(boolean r) {
		
		TypedQuery<Solicitacao> query = manager.createQuery("SELECT s FROM Solicitacao s WHERE s.resposta = :resposta", Solicitacao.class);
		query.setParameter("resposta", r );
		System.out.println("-----------"+query.getResultList().size());
		
		return query.getResultList();	
	}

	
}
