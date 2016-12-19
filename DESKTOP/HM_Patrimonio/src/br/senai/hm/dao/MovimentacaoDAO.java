package br.senai.hm.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import application.Main;
import br.senai.hm.modelo.Movimentacao;

public class MovimentacaoDAO {
	
	EntityManager manager;
	
	public MovimentacaoDAO() {
		manager = Main.getManager();
	}
	
	public List<Movimentacao> buscarTodos() {
		//manager.getTransaction().begin();
		TypedQuery<Movimentacao> query = manager.createQuery("SELECT mv FROM Movimentacao mv Order By mv.ambiente", Movimentacao.class);
		//manager.getTransaction().commit();
		return query.getResultList();
	}
	

}
