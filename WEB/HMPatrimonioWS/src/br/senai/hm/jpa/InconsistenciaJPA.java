package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import br.senai.hm.dao.InconsistenciaDAO;
import br.senai.hm.modelo.Inconsistencia;

@Repository
public class InconsistenciaJPA implements InconsistenciaDAO{
	
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Inconsistencia i) {
		//manager.getTransaction().begin();
		manager.persist(i);;
		//manager.getTransaction().commit();
		
	}

	@Override
	public void alterar(Inconsistencia i) {
	//	manager.getTransaction().begin();
		manager.merge(i);
//		manager.getTransaction().commit();
	}


	@Override
	public List<Inconsistencia> buscarTodos() {
		TypedQuery<Inconsistencia> query = manager.createQuery(
				"SELECT i FROM Inconsistencia i", Inconsistencia.class);
		return query.getResultList();
	}

	@Override
	public Inconsistencia buscarId(Long id) {
		return manager.find(Inconsistencia.class, id);
	}

}
