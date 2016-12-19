package br.senai.hm.dao;

import java.util.List;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;

import application.Main;
import br.senai.hm.modelo.Categoria;

public class CategoriaDAO {
	
	private EntityManager manager;
	
	public CategoriaDAO(){
		manager = Main.getManager();
	}


	public void inserir(Categoria c) {
		manager.getTransaction().begin();
		manager.persist(c);
		manager.getTransaction().commit();	
	}


	public void alterar(Categoria c) {
		manager.getTransaction().begin();
		manager.merge(c);
		manager.getTransaction().commit();
	}
	
	public List<Categoria> buscarTodos(){
		//manager.getTransaction().begin();
		TypedQuery<Categoria> query = manager.createQuery("SELECT c FROM Categoria c", Categoria.class);
	//	manager.getTransaction().commit();
		return query.getResultList();
	}

}
