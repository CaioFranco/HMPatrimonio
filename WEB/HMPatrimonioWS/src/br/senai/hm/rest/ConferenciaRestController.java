package br.senai.hm.rest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.dao.InconsistenciaDAO;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Inconsistencia;
import br.senai.hm.modelo.Patrimonio;

@Transactional
@RestController
@RequestMapping("/services/conferencia")
public class ConferenciaRestController {
	
	@Autowired
	@Qualifier("conferenciaJPA")
	private ConferenciaDAO dao;
	
	@Autowired
	@Qualifier("inconsistenciaJPA")
	private InconsistenciaDAO daoi;
	
	@RequestMapping(value="/lista",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public List<Conferencia> lista(){
		return dao.buscarTodos();
	}
	
	@RequestMapping(value="/salvar",method=RequestMethod.POST,headers="Accept=application/json;charset=UTF-8",consumes = "application/json;charset=UTF-8")
	public String salvar(@RequestBody String json){
		String retorno;
		JSONObject job = new JSONObject(json);
		Conferencia c = new Conferencia();
		System.out.println(job);
		if (!job.isNull("id")) {
			c.setId(job.getLong("id"));
		}
		Date data = new Date(System.currentTimeMillis());
		c.setData(data);
		c.setAgendamento(getAgendamento(c));
		
		List<Patrimonio> ausentes = new ArrayList<Patrimonio>();
		Patrimonio p;
		JSONArray jobArrayPatrimonio = (JSONArray) new JSONTokener(job.getJSONArray("patrimonio_ausente").toString()).nextValue();
		for (int i = 0; i < jobArrayPatrimonio.length(); i++) {
			JSONObject jobPatrimonio = jobArrayPatrimonio.getJSONObject(i); 
			p = new Patrimonio();
			p.setId(jobPatrimonio.getLong("id"));
			ausentes.add(p);
		}
		c.setPatrimonio_ausente(ausentes);	
		dao.alterar(c);
		retorno = "Conferencia Inserida Com Sucesso!!!";
		return retorno;
	}
	
	public Agendamento getAgendamento(Conferencia conferencia){
		Agendamento a = new Agendamento();
		for (Conferencia c : dao.buscarTodos()) {
			if (conferencia.getId().equals(c.getId())) {
				a = c.getAgendamento();
			}
		}
		return a;
	}
	
	public void gerarInconsistencia(List<Patrimonio> lista){
		Inconsistencia i;
		for (Patrimonio p : lista) {
			i = new Inconsistencia();
			i.setPatrimonio(p);
		}
		
	}
	
}
