package br.senai.hm.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import application.Main;
import br.senai.hm.modelo.Modelo;

public class ModeloDAO {
	
	private EntityManager manager ;
	//private Connection connection ;
	
	public ModeloDAO(){
		manager = Main.getManager();
	}
	
	public void inserir(Modelo m){
		manager.getTransaction().begin();
		manager.persist(m);	
		manager.getTransaction().commit();
	}
	public void alterar (Modelo m)	{
		manager.getTransaction().begin();
		manager.merge(m);
		manager.getTransaction().commit();
	}
	public void inativar(Modelo m){
		manager.getTransaction().begin();
		m.setStatus(false);
		manager.merge(m);
		manager.getTransaction().commit();
	}
	
	public List<Modelo> buscarTodos(){
		//manager.getTransaction().begin();
		TypedQuery<Modelo> query = manager.createQuery("SELECT m FROM Modelo m", Modelo.class);
	//	manager.getTransaction().commit();
		return query.getResultList();	
	}
	
	public Modelo buscarId(Long id) {
		return manager.find(Modelo.class, id);
	}

}
