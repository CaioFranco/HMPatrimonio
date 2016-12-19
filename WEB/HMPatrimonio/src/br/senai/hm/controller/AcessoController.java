package br.senai.hm.controller;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.hm.jpa.UsuarioJPA;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;

@Transactional
@Controller
public class AcessoController {

	@Autowired
	private UsuarioJPA daoUser;

	@RequestMapping("/bem_vindo")
	public String bem_vindo() {
		return "bem_vindo";
	}

	@RequestMapping("gestor")
	public String gestor() {
		return "gestor";
	}

	@RequestMapping("acesso/erro01")
	public String erro01() {
		return "acesso/erro01";
	}

	@RequestMapping("/sair")
	public String sair(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}

	@RequestMapping("/acessar")
	public String acessar(String login, String senha, HttpSession sessao) {

		Usuario u = daoUser.acessar(login, senha);
		// System.out.println(u.isStatus());
		if (u != null && u.isStatus()) {
			sessao.setAttribute("usuario", u);
			return "bem_vindo";
		} else {
			return "redirect:/acesso/erro01";
		}
	}

}
