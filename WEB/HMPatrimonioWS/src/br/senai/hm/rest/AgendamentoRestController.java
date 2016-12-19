package br.senai.hm.rest;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.hm.dao.AgendamentoDAO;
import br.senai.hm.modelo.Agendamento;

@Transactional
@RestController
@RequestMapping("/services/agendamento")
public class AgendamentoRestController {
	
	@Autowired
	@Qualifier("agendamentoJPA")
	private AgendamentoDAO daoag;
	
	@RequestMapping(value="/lista",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public List<Agendamento> lista(){

		return daoag.buscarTodos();
	}

}
