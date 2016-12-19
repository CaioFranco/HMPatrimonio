package br.senai.hm.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sun.org.apache.bcel.internal.generic.Type;

import application.Main;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Transferencia;
import br.senai.hm.util.Mensagens;

public class PatrimonioDAO {

	private EntityManager manager;

	public PatrimonioDAO() {
		manager = Main.getManager();
	}

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
		p.setStatus(false);
		System.out.println("Ativo!!!!!!!!!!!!!!!!");
		manager.merge(p);
		manager.getTransaction().commit();
	}

	public void ativar(Patrimonio p) {
		manager.getTransaction().begin();
		p.setStatus(true);
		manager.merge(p);
		manager.getTransaction().commit();
	}

	public List<Patrimonio> buscarTodos() {
		TypedQuery<Patrimonio> query = manager.createQuery("SELECT p FROM Patrimonio p", Patrimonio.class);
		return query.getResultList();
	}
	
	public List<Patrimonio> buscarTodosOrdenado() {
		TypedQuery<Patrimonio> query = manager.createQuery("SELECT p FROM Patrimonio p ORDER BY p.id_ambiente", Patrimonio.class);
		return query.getResultList();
	}
	

	public List<Patrimonio> buscarPorAmbiente(Ambiente a) {
		List<Patrimonio> lista = new ArrayList<Patrimonio>();
		for (Patrimonio p : buscarTodos()) {
			if (p.getId_ambiente().getDescricao().equals(a.getDescricao())) {
				System.out.println(p.getId_ambiente());
				lista.add(p);
			}
		}
		if (lista.isEmpty()) {
			Mensagens.mostrarMsg8();
		}
		return lista;
	}

	public Patrimonio buscarId(Long id) {
		return manager.find(Patrimonio.class, id);
	}
	
	public boolean validarID(Patrimonio pat){
		TypedQuery<Patrimonio> query = manager.createQuery("SELECT p FROM Patrimonio p WHERE p = :pat", Patrimonio.class);
		query.setParameter("pat", pat);
		if (query.getResultList().isEmpty()) {
			return true;
		}else{
			return false;
		}
	}

}
