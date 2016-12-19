package br.senai.hm.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.modelo.Usuario;

@Repository
public class UsuarioJPA implements UsuarioDAO {
	@PersistenceContext
	EntityManager manager;

	public void inserir(Usuario u) {
		manager.getTransaction().begin();
		manager.persist(u);
		manager.getTransaction().commit();
		// manager.close();
	}

	public void alterar(Usuario u) {
		manager.getTransaction().begin();
		manager.merge(u);
		manager.getTransaction().commit();

	}

	public Usuario buscarId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public List<Usuario> buscarTodos() {
		TypedQuery<Usuario> query = manager.createQuery(
				"SELECT u FROM Usuario u", Usuario.class);
		return query.getResultList();
	}

	public void inativar(Usuario u) {
		manager.getTransaction().begin();
		u.setStatus(false);
		manager.merge(u);
		manager.getTransaction().commit();
	}

	public Usuario acessar(String login, String senha) {
		//Resul
		/*TypedQuery<Usuario> query = manager
				.createQuery(
						"SELECT u FROM Usuario u WHERE u.permissao = 1 and u.login = :login AND u.senha = :senha",
						Usuario.class);
		
		query.setParameter("login", login);
		query.setParameter("BINARY senha", senha);

		List<Usuario> lista = query.getResultList();
		if (lista.size() == 0) {
			return null;
		} else {
			return lista.get(0);
		}*/

		for (Usuario u : buscarTodos()) {
			if (u.getLogin().equals(login) && u.getSenha().equals(senha)) {
				return u;
			}
		}
		return null;
	}

}
