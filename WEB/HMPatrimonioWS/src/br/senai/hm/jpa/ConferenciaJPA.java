package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Conferencia;

@Repository
public class ConferenciaJPA implements ConferenciaDAO {
	@PersistenceContext
	EntityManager manager;

	public void inserir(Conferencia c) {
		manager.getTransaction().begin();
		manager.persist(c);
		manager.getTransaction().commit();
	}

	public Conferencia buscarId(Long id) {
		return manager.find(Conferencia.class, id);
	}

	public List<Conferencia> buscarTodos() {
		TypedQuery<Conferencia> query = manager.createQuery(
				"SELECT c FROM Conferencia c", Conferencia.class);
		return query.getResultList();
	}

	@Override
	public void alterar(Conferencia c) {
		//manager.getTransaction().begin();
		manager.merge(c);
	//	manager.getTransaction().commit();
	}
}
