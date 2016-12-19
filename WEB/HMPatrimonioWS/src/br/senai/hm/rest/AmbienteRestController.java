package br.senai.hm.rest;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.modelo.Ambiente;

@Transactional
@RestController
@RequestMapping("/services/ambiente")
public class AmbienteRestController {
	

	@Autowired
	@Qualifier("ambienteJPA")
	private AmbienteDAO daoa;
	
	@RequestMapping(value="/lista",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public List<Ambiente> lista(){

		return daoa.buscarTodos();
	}


}
