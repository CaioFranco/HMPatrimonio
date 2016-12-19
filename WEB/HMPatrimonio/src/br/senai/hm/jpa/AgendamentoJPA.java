package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;





import org.springframework.stereotype.Repository;

import br.senai.hm.dao.AgendamentoDAO;
import br.senai.hm.modelo.Agendamento;


@Repository
public class AgendamentoJPA implements AgendamentoDAO{
	@PersistenceContext
	EntityManager manager;

	public void agendar(Agendamento ag) {
		manager.getTransaction().begin();
		manager.persist(ag);
		manager.getTransaction().commit();

	}
	public void alterar (Agendamento ag) {
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
