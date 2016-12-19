package br.senai.hm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import application.Main;
import br.senai.hm.modelo.Agendamento;

public class AgendamentoDAO {
	EntityManager manager;

	public AgendamentoDAO() {
		manager = Main.getManager();
	}

	public void agendar(Agendamento ag) {
		manager.getTransaction().begin();
		manager.persist(ag);
		manager.getTransaction().commit();

	}

	public void alterar(Agendamento ag) {
		manager.getTransaction().begin();
		manager.merge(ag);
		manager.getTransaction().commit();

	}

	public Agendamento buscarId(Long id) {
		return manager.find(Agendamento.class, id);
	}

	public List<Agendamento> buscarTodos() {
		TypedQuery<Agendamento> query = manager.createQuery("SELECT ag FROM Agendamento ag", Agendamento.class);
		return query.getResultList();
	}

}
