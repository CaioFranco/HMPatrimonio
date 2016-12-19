package br.senai.hm.jpa;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.EmpresaDAO;
import br.senai.hm.modelo.Empresa;

@Repository
public class EmpresaJPA implements EmpresaDAO{
	@PersistenceContext
	EntityManager manager;
	

	
	public void inserir(Empresa e) {
		manager.getTransaction().begin();
		manager.persist(e);
		manager.getTransaction().commit();
		manager.close();
	}
	public void alterar (Empresa e) {
		manager.getTransaction().begin();
		manager.merge(e);
		manager.getTransaction().commit();
		manager.close();
	}
	public Empresa buscarId(Long id) {
		//Empresa e = buscarId(id);
		//manager.getTransaction().begin();
		//manager.find(Empresa.class, id);
		//manager.getTransaction().commit();
		//manager.close();
		return manager.find(Empresa.class, id);
	}


}
