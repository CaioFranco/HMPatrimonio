package br.senai.hm.rest;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.modelo.Usuario;

@Transactional
@RestController
@RequestMapping("/services/usuario")
public class UsuarioRestController {
	
	@Autowired
	@Qualifier("usuarioJPA")
	private UsuarioDAO daou;
	
	@RequestMapping(value="/lista",method=RequestMethod.GET,headers="Accept=application/json;charset=UTF-8")
	public List<Usuario> lista(){

		return daou.buscarTodos();
	}

}
