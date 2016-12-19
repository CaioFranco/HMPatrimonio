package br.senai.hm.modelo;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import br.senai.hm.dao.EmpresaDAO;

@Entity
public class Movimentacao {
	@Id
	@GeneratedValue
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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
	
	public String getNome_fantasia() {
		EmpresaDAO daoe = new EmpresaDAO();
		return daoe.buscarId().getNome_fantasia();
	}
	
	public String getMotivo() {
		return solicitacao.getMotivo();
	}
	

	
}
