package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.TransferenciaDAO;
import br.senai.hm.modelo.Transferencia;

@Repository
public class TrasferenciaJPA implements TransferenciaDAO {
	@PersistenceContext
	EntityManager manager;

	public void transferir(Transferencia t) {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();

	}
	public void alterar (Transferencia t) {
		manager.getTransaction().begin();
		manager.merge(t);
		manager.getTransaction().commit();

	}

	public Transferencia buscarId(Long id) {
		return manager.find(Transferencia.class, id);
	}

	public List<Transferencia> buscarTodos() {
		manager.getTransaction().begin();
		TypedQuery<Transferencia> query = manager.createQuery("SELECT t FROM Transferencia t", Transferencia.class);
		manager.getTransaction().commit();
		return query.getResultList();
	}
}
