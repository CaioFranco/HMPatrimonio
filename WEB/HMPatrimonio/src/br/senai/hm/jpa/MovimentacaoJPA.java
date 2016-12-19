package br.senai.hm.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import br.senai.hm.dao.MovimentacaoDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Movimentacao;
import br.senai.hm.modelo.Solicitacao;

@Repository
public class MovimentacaoJPA implements MovimentacaoDAO {
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void inserir(Movimentacao mv) {
		manager.persist(mv);
	}

	@Override
	public List<Movimentacao> buscarTodos() {
		TypedQuery<Movimentacao> query = manager.createQuery(
				"SELECT mv FROM Movimentacao mv order by mv.id desc", Movimentacao.class);
		return query.getResultList();
	}

	@Override
	public Movimentacao buscarId(Long id) {
		return manager.find(Movimentacao.class, id);
	}

	public List<Movimentacao> buscar_destino(Long id) {
		Ambiente a = manager.find(Ambiente.class, id);
		TypedQuery<Movimentacao> query = manager.createQuery(
				"SELECT m FROM Movimentacao	m WHERE m.ambiente = :a",
				Movimentacao.class);
		query.setParameter("a", a);

		return query.getResultList();

	}
}
