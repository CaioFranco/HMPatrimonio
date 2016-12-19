package br.senai.hm.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.hm.dao.AgendamentoDAO;
import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.dao.ModeloDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Modelo;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Solicitacao;
import br.senai.hm.modelo.Usuario;

@Controller
public class ConferenciaController {

	@Autowired
	@Qualifier("conferenciaJPA")
	private ConferenciaDAO daoC;

	@Autowired
	@Qualifier("ambienteJPA")
	private AmbienteDAO daoA;

	@Autowired
	@Qualifier("patrimonioJPA")
	private PatrimonioDAO daoP;

	@Autowired
	@Qualifier("modeloJPA")
	private ModeloDAO daoMod;

	@Autowired
	@Qualifier("agendamentoJPA")
	private AgendamentoDAO daoAg;

	@RequestMapping("/registro_conferencia")
	public String registro_conferencia(Model model,HttpSession sessao) {
		Usuario u = (Usuario) sessao.getAttribute("usuario");
		List<Conferencia> lista = daoC.buscar_ConferenciaUsuario(u);
		model.addAttribute("conferencia", lista);

		return "conferencia/registro_conferencia";
	}

	@RequestMapping("/detalhar_conferencia")
	public String detalhar_conferencia(Model model, String id,
			HttpSession sessao) {
		Conferencia conf = daoC.buscarId(Long.parseLong(id));
		model.addAttribute("conferencia", conf);

		List<Patrimonio> lista_p = daoP.buscarTodos();
		model.addAttribute("patrimonios", lista_p);

		Agendamento lista_ag = daoAg.buscarId(Long.parseLong(id));
		model.addAttribute("agendamento", lista_ag);

		sessao.setAttribute("conferencia", conf);

		return "conferencia/detalhar_conferencia";

	}

	@RequestMapping("/busca_status")
	public String busca_status(Model model, String s, String id,
			HttpSession sessao) {
		Conferencia conf = (Conferencia) sessao.getAttribute("conferencia");

		List<Patrimonio> ppresente = daoP.buscarTodos();
		ppresente.removeAll(conf.getPatrimonio_ausente());

		if (s.equalsIgnoreCase("Presente")) {
			model.addAttribute("patrimonios", ppresente);
		} else if (s.equalsIgnoreCase("Ausente")) {
			model.addAttribute("patrimonios", conf.getPatrimonio_ausente());
		} else if (s.equalsIgnoreCase("Escolha o status")) {
			model.addAttribute("patrimonios", daoP.buscarTodos());
		}

		return "conferencia/detalhar_conferencia";

	}

}
