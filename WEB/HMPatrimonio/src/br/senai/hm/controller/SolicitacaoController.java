package br.senai.hm.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.ModeloDAO;
import br.senai.hm.dao.MovimentacaoDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.dao.SolicitacaoDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Modelo;
import br.senai.hm.modelo.Movimentacao;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Solicitacao;
import br.senai.hm.modelo.Usuario;

@Transactional
@Controller
public class SolicitacaoController {

	@Autowired
	@Qualifier("patrimonioJPA")
	private PatrimonioDAO daoP;

	@Autowired
	@Qualifier("modeloJPA")
	private ModeloDAO daoM;

	@Autowired
	@Qualifier("ambienteJPA")
	private AmbienteDAO daoA;

	@Autowired
	@Qualifier("solicitacaoJPA")
	private SolicitacaoDAO daoS;

	@Autowired
	@Qualifier("movimentacaoJPA")
	private MovimentacaoDAO daoMov;

	@RequestMapping("/solicitar_movimentacao")
	public String solicitar_movimentacao(Model model, HttpSession sessao) {
		
		Ambiente amb_user = daoA.buscarUsuario((Usuario) sessao.getAttribute("usuario"));
		List<Patrimonio> lista = daoP.buscar_PatrimonioUsuario(amb_user);

		model.addAttribute("patrimonio", lista);


		List<Ambiente> lista_a = daoA.buscarAtivos();
		lista_a.remove(amb_user);
		model.addAttribute("ambiente", lista_a);

	
		System.out.println(amb_user);
		model.addAttribute("amb", amb_user);

		return "solicitacao/solicitar_movimentacao";
	}

	@RequestMapping("/responder_solicitacao")
	public String responder_solicitacao(Model model,HttpSession sessao) {
		Usuario u = (Usuario) sessao.getAttribute("usuario");

		List<Solicitacao> lista_s = daoS.buscar_SolitacaoUsuario(u);
		model.addAttribute("solicitacao", lista_s);
		System.out.println(lista_s);

		return "solicitacao/responder_solicitacao";
	}

	@RequestMapping("/rejeitar")
	public String rejeitar(Solicitacao s, Model model, String id) {

		s.setAmbiente_destino(s.getAmbiente_destino());

		Solicitacao solit = daoS.buscarId(Long.parseLong(id));
		model.addAttribute("solicitacao", solit);
		daoS.rejeitar(s);
	
		System.out.println("operação ja efetuada");
		return "solicitacao/rejeitada";
	}

	@RequestMapping("/permitir")
	public String permitir(Solicitacao s, String id, Model model) {

		Solicitacao solit = daoS.buscarId(Long.parseLong(id));
		model.addAttribute("solicitacao", solit);
		Ambiente ambiente = solit.getAmbiente_destino();
		System.out.println(ambiente.getId()+ambiente.getDescricao());
		daoS.permitir(s);

			for (Patrimonio p : solit.getPatrimonios()) {
				Calendar dt = Calendar.getInstance();
				Movimentacao mv = new Movimentacao();
				mv.setData(dt);
				mv.setAmbiente(solit.getAmbiente_destino());
				mv.setSolicitacao(solit);
				mv.setId(null);
				p.setAmbiente(ambiente);
				mv.setPatrimonio(p);
				daoP.alterar(p);
				System.out.println("2" + solit.isResposta());
				System.out.println("insere");
				daoMov.inserir(mv);
			}

		return "solicitacao/permitida";
	}

	@RequestMapping("/registro_solicitacao")
	public String registro_solicitacao(Model model,HttpSession sessao) {
		Usuario u = (Usuario) sessao.getAttribute("usuario");
		List<Patrimonio> lista = daoP.buscarTodos();
		model.addAttribute("patrimonio", lista);

		List<Ambiente> lista_a = daoA.buscarAtivos();
		model.addAttribute("ambiente", lista_a);

		List<Solicitacao> lista_s = daoS.buscar_SolicitacaoRespondida(u);
		model.addAttribute("solicitacao", lista_s);

		return "solicitacao/registro_solicitacao";
	}

	@RequestMapping("/detalhar_solicitacao")
	public String detalhar_solicitacao(Model model, String id) {
		Solicitacao solit = daoS.buscarId(Long.parseLong(id));
		model.addAttribute("solicitacao", solit);
		return "solicitacao/detalhar_solicitacao";
	}

	@RequestMapping("/AddSolicitacao")
	public String addSolicitacao(Long[] patrimonio, Solicitacao s, Long id) {

		if (s.getId() == null) {

			List<Patrimonio> ps = new ArrayList<Patrimonio>();
			if (ps.size()<0) {
				return "solicitacao/falha";
			} else {
			for (int i = 0; i < patrimonio.length; i++) {

				System.out.println(patrimonio[i]);

				for (Patrimonio p : daoP.buscarTodos()) {
					if (patrimonio[i] == p.getId()) {
						ps.add(p);
						System.out.println("entrou!!!!!!!!");
					}
				}
			}
			
			s.setAmbiente_origem(ps.get(0).getAmbiente());
			s.setResposta(true);
			s.setPatrimonios(ps);
			
			daoS.realizar(s);
			}

		}
		return "solicitacao/sucesso";
	}

	@RequestMapping("/busca_destino")
	public String busca(Model model, String idAmbiente) {
		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);

		try {
			model.addAttribute("solicitacao",
					daoS.buscar_destino(Long.parseLong(idAmbiente)));
		} catch (NumberFormatException e) {
			return "forward:registro_solicitacao";
		}

		return "solicitacao/registro_solicitacao";

	}
	
	@RequestMapping("/busca_patrimonio")
	public String busca_patrimonio(Model model, String idAmbiente, HttpSession sessao) {
		
		Ambiente amb_user = daoA.buscarUsuario((Usuario) sessao.getAttribute("usuario"));
		model.addAttribute("amb", amb_user);
		
		List<Ambiente> lista_a = daoA.buscarAtivos();
		lista_a.remove(amb_user);
		
		model.addAttribute("ambiente", lista_a);

		try {
			model.addAttribute("patrimonio", daoP.buscar_ambiente(Long.parseLong(idAmbiente)));
		} catch (NumberFormatException e) {
			return "forward:solicitar_movimentacao";
		}

		return "solicitacao/solicitar_movimentacao";

	}

	@RequestMapping("/busca_resposta")
	public String busca_resposta(Model model, String r) {
		List<Ambiente> lista_a = daoA.buscarTodos();
		model.addAttribute("ambiente", lista_a);

		if (r.equalsIgnoreCase("permitada")) {
			model.addAttribute("solicitacao", daoS.buscar_resposta(true));
		} else if (r.equalsIgnoreCase("rejeitada")) {
			model.addAttribute("solicitacao", daoS.buscar_resposta(false));
		} else if (r.equalsIgnoreCase("Escolha a resposta")) {
			return "forward:registro_solicitacao";
		}

		return "solicitacao/registro_solicitacao";
	}

	@RequestMapping("home")
	public String home() {
		return "bem_vindo";
	}
}
