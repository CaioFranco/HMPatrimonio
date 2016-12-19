package br.senai.hm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import application.Main;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Transferencia;

public class ConferenciaDAO {
	EntityManager manager;

	public ConferenciaDAO() {
		manager = Main.getManager();
	}

	public void inserir(Conferencia c) {
		manager.getTransaction().begin();
		manager.persist(c);
		manager.getTransaction().commit();

	}

	public Conferencia buscarId(Long id) {
		return manager.find(Conferencia.class, id);
	}

	public List<Conferencia> buscarTodos() {
		TypedQuery<Conferencia> query = manager.createQuery("SELECT c FROM Conferencia c", Conferencia.class);

		return query.getResultList();
	}

	public Conferencia buscarPorAgendamento(Ambiente a) {
		TypedQuery<Conferencia> query = manager.createQuery(
				"SELECT c FROM Conferencia c WHERE c.agendamento.ambiente = :ambiente AND c.data = null",
				Conferencia.class);
		query.setParameter("ambiente", a);
		System.out.println("SIZE: " + query.getResultList().size());
		if (query.getResultList().size() > 0) {
			return query.getResultList().get(0);
		} else {
			return null;
		}

	}
}
