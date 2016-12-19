package br.senai.hm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.ModeloDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Situacao;

@Controller
public class PatrimonioController {

	@Autowired
	@Qualifier("patrimonioJPA")
	private PatrimonioDAO daoP;

	@Autowired
	@Qualifier("modeloJPA")
	private ModeloDAO daoM;

	@Autowired
	@Qualifier("ambienteJPA")
	private AmbienteDAO daoA;

	@RequestMapping("/registro_patrimonio")
	public String registro_patrimonio(Model model) {
		List<Patrimonio> lista_pat = daoP.buscarTodos();
		model.addAttribute("patrimonio", lista_pat);

		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);

		return "patrimonio/registro_patrimonio";
	}

	@RequestMapping("busca_identificador")
	public String busca_identificador(Model model, Long identificador) {
		List<Patrimonio> lista_id = daoP.buscar_identificador(identificador);
		model.addAttribute("patrimonio", lista_id);
		return "patrimonio/registro_patrimonio";
	}

	@RequestMapping("busca_descricao")
	public String buscar_descricao(Model model, String descricao) {
		List<Patrimonio> lista_p = daoP.buscar_descricao(descricao);
		model.addAttribute("patrimonio", lista_p);
		return "patrimonio/registro_patrimonio";
	}

	@RequestMapping("busca_modelo")
	public String buscar_modelo(Model model, String componente) {
		List<Patrimonio> lista_p = daoP.buscar_modelo(componente);
		model.addAttribute("patrimonio", lista_p);
		return "patrimonio/registro_patrimonio";
	}

	@RequestMapping("/busca_ambiente")
	public String busca_ambiente(Model model, String idAmbiente) {
		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);

		try {
			model.addAttribute("patrimonio",
					daoP.buscar_ambiente(Long.parseLong(idAmbiente)));
		} catch (NumberFormatException e) {
			return "forward:registro_patrimonio";
		}

		return "patrimonio/registro_patrimonio";

	}

	@RequestMapping("/busca_situacao")
	public String busca_resposta(Model model, String s) {
		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);

		if (s.equalsIgnoreCase("Novo")) {
			model.addAttribute("patrimonio",
					daoP.buscar_situacao(Situacao.NOVO));
		} else if (s.equalsIgnoreCase("Seminovo")) {
			model.addAttribute("patrimonio",
					daoP.buscar_situacao(Situacao.SEMINOVO));
		} else if (s.equalsIgnoreCase("selecione uma situação")) {
			return "forward:registro_patrimonio";
		}

		return "patrimonio/registro_patrimonio";
	}

}
