package br.senai.hm.modelo;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class Solicitacao implements Serializable {

	private Long id;
	private String motivo;
	private Calendar data;
	private boolean resposta;
	private Ambiente ambiente_origem;
	private Ambiente ambiente_destino;
	private List<Patrimonio> patrimonios;

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMotivo() {
		return motivo;
	}


	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}


	public Calendar getData() {
		return data;
	}


	public void setData(Calendar data) {
		this.data = data;
	}


	public boolean isResposta() {
		return resposta;
	}


	public void setResposta(boolean resposta) {
		this.resposta = resposta;
	}


	public Ambiente getAmbiente_origem() {
		return ambiente_origem;
	}


	public void setAmbiente_origem(Ambiente ambiente_origem) {
		this.ambiente_origem = ambiente_origem;
	}


	public Ambiente getAmbiente_destino() {
		return ambiente_destino;
	}


	public void setAmbiente_destino(Ambiente ambiente_destino) {
		this.ambiente_destino = ambiente_destino;
	}


	public List<Patrimonio> getPatrimonio() {
		return patrimonios;
	}


	public void setPatrimonio(List<Patrimonio> patrimonio) {
		this.patrimonios = patrimonio;
	}
	

	
}
