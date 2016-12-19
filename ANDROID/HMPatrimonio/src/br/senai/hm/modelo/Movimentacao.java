package br.senai.hm.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class Movimentacao implements Serializable {

	private Long id;
	private Calendar data;
	private Ambiente ambiente;
	private Solicitacao solicitacao;
	private Patrimonio patrimonio;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getData() {
		return new Date();
	}


	public void setData(Calendar data) {
		this.data = data;
	}


	public Ambiente getAmbiente() {
		return ambiente;
	}


	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}


	public Solicitacao getSolicitacao() {
		return solicitacao;
	}


	public void setSolicitacao(Solicitacao solicitacao) {
		this.solicitacao = solicitacao;
	}


	public String getPatrimonio() {
		return patrimonio.getId_modelo().getDescricao();
	}

	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}
	
	public String getMotivo() {
		return solicitacao.getMotivo();
	}
	

	
}
