package br.senai.hm.dao;


import javax.persistence.EntityManager;
import application.Main;
import br.senai.hm.modelo.Empresa;

public class EmpresaDAO {
	EntityManager manager;


	public EmpresaDAO() {
		manager = Main.getManager();
	}

	public void inserir(Empresa e) {
		manager.getTransaction().begin();
		manager.persist(e);
		manager.getTransaction().commit();
	}
	public void alterar (Empresa e) {
		manager.getTransaction().begin();
		manager.merge(e);
		manager.getTransaction().commit();
	}
	public Empresa buscarId() {
		Long id =  1L;
		return manager.find(Empresa.class, id);
	}


}
