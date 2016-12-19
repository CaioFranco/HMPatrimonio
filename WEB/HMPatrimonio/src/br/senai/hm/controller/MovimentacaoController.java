package br.senai.hm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.MovimentacaoDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.dao.SolicitacaoDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Movimentacao;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Solicitacao;

@Controller
public class MovimentacaoController {
	@Autowired
	@Qualifier("ambienteJPA")
	private AmbienteDAO daoA;

	@Autowired
	@Qualifier("movimentacaoJPA")
	private MovimentacaoDAO daoMov;
	
	@Autowired
	@Qualifier("patrimonioJPA")
	private PatrimonioDAO daoP;
	

	@RequestMapping("/registro_movimentacao")
	public String registro_movimentacao(Model model) {
		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);
		
		List<Movimentacao> lista = daoMov.buscarTodos();
		model.addAttribute("movimentacao", lista);
		return "movimentacao/registro_movimentacao";
	}

	@RequestMapping("/detalhar_movimentacao")
	public String detalhar_movimentacao(Model model, String id) {
		Movimentacao movim = daoMov.buscarId(Long.parseLong(id));
		model.addAttribute("movimentacao", movim);
		
		
		
		Patrimonio p = daoP.buscarId(movim.getPatrimonio().getId());
		model.addAttribute("patrimonio", p);		
		
		
		return "movimentacao/detalhar_movimentacao";
	}
	
	@RequestMapping("/buscar_destino")
	public String busca(Model model, String idAmbiente) {
		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);

		try {
			model.addAttribute("movimentacao",
					daoMov.buscar_destino(Long.parseLong(idAmbiente)));
		} catch (NumberFormatException e) {
			return "forward:registro_movimentacao";
		}

		return "movimentacao/registro_movimentacao";

	}
}
