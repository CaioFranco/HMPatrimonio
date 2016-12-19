package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Solicitacao;
import br.senai.hm.modelo.Usuario;

@Repository
public class AmbienteJPA implements AmbienteDAO {
	@PersistenceContext
	EntityManager manager;

	public void inserir(Ambiente a) {
		manager.getTransaction().begin();
		manager.persist(a);
		manager.getTransaction().commit();

	}

	public void alterar(Ambiente a) {
		manager.getTransaction().begin();
		manager.merge(a);
		manager.getTransaction().commit();

	}

	public Ambiente buscarId(Long id) {
		return manager.find(Ambiente.class, id);
	}

	public List<Ambiente> buscarTodos() {
		TypedQuery<Ambiente> query = manager.createQuery(
				"SELECT a FROM Ambiente a", Ambiente.class);
		return query.getResultList();
	}

	public void inativar(Ambiente a) {
		manager.getTransaction().begin();
		a.setStatus(false);
		manager.merge(a);
		manager.getTransaction().commit();
	}

	public Ambiente buscarUsuario(Usuario u) {
		TypedQuery<Ambiente> query = manager.createQuery(
				"SELECT a FROM Ambiente a WHERE a.usuario.id = :usuario",Ambiente.class).setParameter("usuario", u.getId());
		List<Ambiente> lista_a = query.getResultList();
		if (lista_a.size() != 0) {
			System.out.println("ambiente.." + lista_a.get(0));
			return lista_a.get(0);
		}
		return null;

	}
	


}
