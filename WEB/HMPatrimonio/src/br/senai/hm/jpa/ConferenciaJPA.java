package br.senai.hm.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.mysql.fabric.xmlrpc.base.Array;

import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Usuario;

@Repository
public class ConferenciaJPA implements ConferenciaDAO {
	@PersistenceContext
	EntityManager manager;

	public void inserir(Conferencia c) {
		manager.getTransaction().begin();
		manager.persist(c);
		manager.getTransaction().commit();

	}

	public Conferencia buscarId(Long id) {
		return manager.find(Conferencia.class, id);
	}

	public List<Conferencia> buscarTodos() {
		TypedQuery<Conferencia> query = manager.createQuery(
				"SELECT c FROM Conferencia c order by c.id desc", Conferencia.class);
		return query.getResultList();
	}
	
	public List<Conferencia> buscar_ConferenciaUsuario(Usuario u){
		List<Conferencia> lista = new ArrayList<Conferencia>();
		for (Conferencia c : buscarTodos()) {
			if (c.getAgendamento().getAmbiente().getUsuario().getId().equals(u.getId())) {
				lista.add(c);
			}
		}
		return lista;	
	}
	
}
