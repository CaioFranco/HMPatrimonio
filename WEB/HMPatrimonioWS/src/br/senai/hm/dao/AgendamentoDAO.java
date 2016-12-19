package br.senai.hm.dao;

import java.util.List;

import br.senai.hm.modelo.Agendamento;

public interface AgendamentoDAO {
	public void agendar(Agendamento ag);
	public void alterar (Agendamento ag);
	public Agendamento buscarId(Long id);
	public List<Agendamento> buscarTodos();
}
