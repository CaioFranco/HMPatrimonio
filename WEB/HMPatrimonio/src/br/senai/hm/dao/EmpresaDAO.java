package br.senai.hm.dao;

import br.senai.hm.modelo.Empresa;

public interface EmpresaDAO {

	public void inserir(Empresa e);
	public void alterar (Empresa e);
	public Empresa buscarId(Long id);

}
