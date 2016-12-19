package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.ModeloDAO;
import br.senai.hm.modelo.Modelo;
import br.senai.hm.modelo.Solicitacao;

@Repository
public class ModeloJPA implements ModeloDAO {
	@PersistenceContext	
	private EntityManager manager ;
		
	
	
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
		TypedQuery<Modelo> query = manager.createQuery("SELECT m FROM Modelo m", Modelo.class);
		return query.getResultList();	
	}
	
	public Modelo buscarId(Long id) {
		return manager.find(Modelo.class, id);
	}
	


}
