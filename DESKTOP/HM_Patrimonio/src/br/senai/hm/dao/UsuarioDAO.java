package br.senai.hm.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;

import application.LoginDuplicadoException;
import application.Main;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.Mensagens;

public class UsuarioDAO {
	EntityManager manager;

	public UsuarioDAO() {
		manager = Main.getManager();
	}

	public void inserir(Usuario u) throws Exception {
		try {
			manager.getTransaction().begin();
			manager.persist(u);
			manager.getTransaction().commit();
		} catch (PersistenceException e) {
			try {
				manager.getTransaction().rollback();
			} catch (Exception e2) {
				System.out.println("ERRO NO ROLLBACK");
			}
			Throwable t = e.getCause();
			Throwable t1 = t.getCause();
			if (t1 instanceof com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException) {
				if (t1.getMessage().toLowerCase().contains("duplicate")) {
					throw new LoginDuplicadoException();
				}

			}
		}

	}

	public void alterar(Usuario u) {
		manager.getTransaction().begin();
		manager.merge(u);
		manager.getTransaction().commit();
	}

	public Usuario buscarId(Long id) {
		return manager.find(Usuario.class, id);
	}

	public Usuario validarUsuario(String login, String senha) {
		Usuario usuario = null;
		for (Usuario u : buscarTodos()) {
			if (login.equals(u.getLogin()) && senha.equals(u.getSenha())
					&& u.getPermissao() == Permissao.ADMINISTRADOR) {
				usuario = u;
			}
		}
		return usuario;
	}


	public Usuario validarUsuario2(String login) {
		Usuario usuario = null;
		for (Usuario u : buscarTodos()) {
			if (login.equals(u.getLogin())
					&& u.getPermissao() == Permissao.ADMINISTRADOR) {
				usuario = u;
			}
		}
		return usuario;
	}
	public List<Usuario> buscarTodos() {
		TypedQuery<Usuario> query = manager.createQuery("SELECT u FROM Usuario u", Usuario.class);
		return query.getResultList();
	}

	public List<Usuario> buscarAdministrador() {
		List<Usuario> lista = new ArrayList<Usuario>();
		if (buscarTodos().size() != 0) {
			for (Usuario u : buscarTodos()) {
				if (u.getPermissao() == Permissao.ADMINISTRADOR) {
					lista.add(u);
				}

			}
		} else {
			return null;
		}

		return lista;
	}

	public void inativar(Usuario u) {
		manager.getTransaction().begin();
		u.setStatus(false);
		manager.merge(u);
		manager.getTransaction().commit();
	}

}
