package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Situacao;

@Repository
public class PatrimonioJPA implements PatrimonioDAO {
	@PersistenceContext
	private EntityManager manager;

	public void inserir(Patrimonio p) {
		manager.getTransaction().begin();
		manager.merge(p);
		manager.getTransaction().commit();
	}

	public void alterar(Patrimonio p) {
		manager.getTransaction().begin();
		manager.merge(p);
		manager.getTransaction().commit();
	}

	public void inativar(Patrimonio p) {
		manager.getTransaction().begin();
		// m.setStatus(false);
		manager.merge(p);
		manager.getTransaction().commit();
	}

	public List<Patrimonio> buscarTodos() {
		TypedQuery<Patrimonio> query = manager.createQuery(
				"SELECT p FROM Patrimonio p", Patrimonio.class);
		return query.getResultList();
	}

	public Patrimonio buscarId(Long id) {
		return manager.find(Patrimonio.class, id);
	}

	public List<Patrimonio> buscar_status(boolean s) {
		TypedQuery<Patrimonio> query = manager.createQuery(
				"SELECT p FROM  Patrimonio p WHERE modelo.status = :status",
				Patrimonio.class);
		query.setParameter("status", s);
		return query.getResultList();
	}

	public List<Patrimonio> buscar_descricao(String descricao) {
		TypedQuery<Patrimonio> query = manager
				.createQuery(
						"SELECT p FROM Patrimonio p WHERE  modelo.descricao LIKE UPPER(:descricao)",
						Patrimonio.class);
		query.setParameter("descricao", "%" + descricao.toUpperCase() + "%");
		return query.getResultList();
	}

	public List<Patrimonio> buscar_modelo(String componente) {
		TypedQuery<Patrimonio> query = manager
				.createQuery(
						"SELECT p FROM Patrimonio p WHERE  modelo.componente LIKE UPPER(:componente)",
						Patrimonio.class);
		query.setParameter("componente", "%" + componente.toUpperCase() + "%");
		return query.getResultList();
	}

	public List<Patrimonio> buscar_ambiente(Long id) {
		Ambiente a = manager.find(Ambiente.class, id);
		TypedQuery<Patrimonio> query = manager.createQuery("SELECT p FROM Patrimonio p WHERE p.ambiente = :a",
				Patrimonio.class);
		query.setParameter("a", a);
		return query.getResultList();

	}
	
	public List<Patrimonio> buscar_situacao(Situacao s){
		TypedQuery<Patrimonio> query = manager.createQuery("SELECT p FROM Patrimonio p WHERE p.situacao = :situacao", Patrimonio.class);
		query.setParameter("situacao", s );
		return query.getResultList();
	}

}
