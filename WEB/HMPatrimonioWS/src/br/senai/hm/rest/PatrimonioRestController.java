package br.senai.hm.rest;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Patrimonio;

@Transactional
@RestController
@RequestMapping("/services/patrimonio")
public class PatrimonioRestController {
	
	@Autowired
	@Qualifier("patrimonioJPA")
	private PatrimonioDAO daop;
	
	@RequestMapping(value="/lista",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public List<Patrimonio> lista(){
		/*List<Patrimonio> lista = new ArrayList<Patrimonio>();
		lista.add(daop.buscarTodos().get(0));*/
		List<Patrimonio> lista = new ArrayList<Patrimonio>();
		for (Patrimonio p : daop.buscarTodos()) {
			p.getId();
			p.getModelo().getDescricao();
			p.getModelo().getComponente();
			lista.add(p);
			
		}
		return lista;
	}
	
	@RequestMapping(value="/patrimonio_id/{id}",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public Patrimonio buscarId(@PathVariable(value="id") Long id){
		return daop.buscarId(id);
	}

}
