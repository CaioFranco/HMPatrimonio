package br.senai.hm.rest;

import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.dao.InconsistenciaDAO;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Inconsistencia;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Situacao;

@Transactional
@RestController
@RequestMapping("/services/inconsistencia")
public class InconsistenciaRestController {
	
	@Autowired
	@Qualifier("inconsistenciaJPA")
	private InconsistenciaDAO daoi;
	
	@Autowired
	@Qualifier("conferenciaJPA")
	private ConferenciaDAO daoc;
	
	@RequestMapping(value="/lista",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public List<Inconsistencia> lista(){

		return daoi.buscarTodos();
	}
	
	@RequestMapping(value="/salvar",method=RequestMethod.POST,headers="Accept=application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
	public String salvar(@RequestBody String strJson){
		String retorno;
		JSONObject job = new JSONObject(strJson);
		Inconsistencia i = new Inconsistencia();
		System.out.println(job);
		if (!job.isNull("id")) {
			i.setId(job.getLong("id"));
		}
		if (!job.isNull("motivo")) {
			i.setMotivo(job.getString("motivo"));
		}
	
		JSONObject jobConferencia = job.getJSONObject("id_conferencia");
		Conferencia c = new Conferencia();
		c.setId(jobConferencia.getLong("id"));
		i.setConferencia(daoc.buscarId(c.getId()));

		JSONObject jobPatrimonio = job.getJSONObject("id_patrimonio");
		Patrimonio p = new Patrimonio();
		p.setId(jobPatrimonio.getLong("id"));
		Date data = new Date(jobPatrimonio.getLong("data_entrada"));
		p.setData_entrada(data);
		p.setSituacao(Situacao.valueOf(jobPatrimonio.getString("situacao")));
		p.setStatus(jobPatrimonio.getBoolean("status"));
		p.setValor_unitario(jobPatrimonio.getDouble("valor_unitario"));

		i.setPatrimonio(p);

		if (i.getId() == null) {
			daoi.inserir(i);
		}else{
			daoi.alterar(i);
			System.out.println("Alterou!!!!!!!!!!!!!!!!!!!!");
		}
		retorno = "Inconsistencia "+i.getId()+" inserido com Sucesso!!!";
		
		return retorno;
	}

	@RequestMapping(value="/alterar",method=RequestMethod.POST,headers="Accept=application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
	public String alterar(@RequestBody String strJson){
		String retorno;
		JSONObject job = new JSONObject(strJson);
		Inconsistencia i = new Inconsistencia();
		System.out.println(job);
		i.setId(job.getLong("id"));
		i.setMotivo(job.getString("motivo"));

		JSONObject jobConferencia = job.getJSONObject("id_conferencia");
		Conferencia c = new Conferencia();
		c.setId(jobConferencia.getLong("id"));
		i.setConferencia(daoc.buscarId(c.getId()));

		JSONObject jobPatrimonio = job.getJSONObject("id_patrimonio");
		Patrimonio p = new Patrimonio();
		p.setId(jobPatrimonio.getLong("id"));

		i.setPatrimonio(p);
		if (i.getId() == null) {
			daoi.inserir(i);
		}else{
			daoi.alterar(i);
			System.out.println("Alterou!!!!!!!!!!!!!!!!!!!!");
		}
		retorno = "Inconsistencia "+i.getId()+" inserido com Sucesso!!!";
		
		return retorno;
	}
	
	public boolean conferirPatrimonio(Patrimonio p){
		for (Inconsistencia i : daoi.buscarTodos()) {
			if (i.getPatrimonio().getId().equals(p.getId())) {
				return true;
			}
		}
		return false;
	}

}
