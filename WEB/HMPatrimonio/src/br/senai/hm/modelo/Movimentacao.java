package br.senai.hm.modelo;

import java.util.Calendar;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Movimentacao {
	@Id
	@GeneratedValue
	private Long id;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar data;
	
	@OneToOne
	@JoinColumn(name = "id_ambiente")
	private Ambiente ambiente;
	
	@OneToOne
	@JoinColumn(name = "id_solicitacao")
	private Solicitacao solicitacao;
	
	
	@OneToOne
	@JoinColumn(name = "id_patrimonio")
	private Patrimonio patrimonio;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Calendar getData() {
		return data;
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


	public Patrimonio getPatrimonio() {
		return patrimonio;
	}


	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}
	
	
	
}
