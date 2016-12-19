package br.senai.hm.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import application.Main;
import br.senai.hm.modelo.Fornecedor;

public class FornecedorDAO {
	
	private EntityManager manager;
	
	public FornecedorDAO(){
		manager = Main.getManager();
	}


	public void inserir(Fornecedor f) {
		manager.getTransaction().begin();
		manager.persist(f);
		manager.getTransaction().commit();	
	}


	public void alterar(Fornecedor f) {
		manager.getTransaction().begin();
		manager.merge(f);
		manager.getTransaction().commit();
		
	}
	
	public List<Fornecedor> buscarTodos(){
		manager.getTransaction().begin();
		TypedQuery<Fornecedor> query = manager.createQuery("SELECT f FROM Fornecedor f", Fornecedor.class);
		manager.getTransaction().commit();
		return query.getResultList();
	}
}
