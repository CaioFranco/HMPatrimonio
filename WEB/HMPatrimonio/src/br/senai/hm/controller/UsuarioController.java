package br.senai.hm.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.modelo.Usuario;

@Transactional
@Controller
public class UsuarioController {

	@Autowired
	@Qualifier("usuarioJPA")
	private UsuarioDAO dao;

	@RequestMapping("/form_administrador")
	public String form(Model model) {
		// model.addAttribute("sexos", Sexo.values());
		return "administrador/formulario";
	}

	@RequestMapping("/AddAdministrador")
	public String addAdministrador(Usuario u) {
		
		if (u.getId() == null) {
			dao.inserir(u);
		} else {
			dao.alterar(u);
			
		}

		return "administrador/sucesso";
	}

	@RequestMapping("/lista_administrador")
	public String listaAdministrador(Model model) {
		List<Usuario> lista = dao.buscarTodos();
		model.addAttribute("administrador", lista);
		return "administrador/lista";
	}

	

	@RequestMapping("/alterar_administrador")
	public String alterarAdministrador(Long id, Model model, Usuario u) {
		Usuario a = dao.buscarId(id);
		model.addAttribute("administrador", a);
		model.addAttribute("u", UsuarioDAO.class);
		return "administrador/formulario";
	}

}
