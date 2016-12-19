package br.senai.hm.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import application.Main;
import br.senai.hm.modelo.Ambiente;

public class AmbienteDAO {
	EntityManager manager;

	public AmbienteDAO() {
		manager = Main.getManager();
	}


	public void inserir(Ambiente a) {
		manager.getTransaction().begin();
		manager.persist(a);
		manager.getTransaction().commit();

	}
	public void alterar (Ambiente a) {
		manager.getTransaction().begin();
		manager.merge(a);
		manager.getTransaction().commit();

	}

	public Ambiente buscarId(Long id) {
		return manager.find(Ambiente.class, id);
	}

	public List<Ambiente> buscarTodos() {

		TypedQuery<Ambiente> query = manager.createQuery("SELECT a FROM Ambiente a", Ambiente.class);
	
		return query.getResultList();
	}

	public void inativar(Ambiente a) {
		manager.getTransaction().begin();
		a.setStatus(false);
		a.setUsuario(null);
		manager.merge(a);
		manager.getTransaction().commit();
	}

}
