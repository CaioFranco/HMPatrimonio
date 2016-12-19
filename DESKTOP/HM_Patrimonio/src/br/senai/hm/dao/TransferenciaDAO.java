package br.senai.hm.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

import application.Main;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Transferencia;

public class TransferenciaDAO {
	EntityManager manager;

	public TransferenciaDAO() {
		manager = Main.getManager();
	}

	public void transferir(Transferencia t, Patrimonio p) {
		manager.getTransaction().begin();
		manager.persist(t);
		if (p.isStatus()) {
			p.setStatus(false);
			manager.merge(p);
			manager.getTransaction().commit();
		}else {
			p.setStatus(true);
			manager.merge(p);
			manager.getTransaction().commit();
		}
	}

	public void alterar(Transferencia t) {
		manager.getTransaction().begin();
		manager.merge(t);
		manager.getTransaction().commit();

	}

	public Transferencia buscarId(Long id) {
		return manager.find(Transferencia.class, id);
	}

	public List<Transferencia> buscarTodos() {
		TypedQuery<Transferencia> query = manager.createQuery("SELECT t FROM Transferencia t Order by t.ambiente", Transferencia.class);
		return query.getResultList();
	}

	public Transferencia buscarMotivo(Patrimonio p) {
		TypedQuery<Transferencia> query = manager
				.createQuery("SELECT t FROM Transferencia t WHERE t.patrimonios = :patrimonios", Transferencia.class);
		query.setParameter("patrimonios", p);
		List<Transferencia> lt = query.getResultList();

		return lt.get(lt.size() - 1);
	}
}
