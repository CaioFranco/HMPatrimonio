package br.senai.hm.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.senai.hm.dao.EmpresaDAO;

@Entity
public class Patrimonio {
	@Id
	private Long id;
	private Date data_entrada;
	private Situacao situacao;
	private double valor_unitario;
	private boolean status;

	@OneToOne
	@JoinColumn(name = "id_ambiente")
	private Ambiente id_ambiente;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "id_modelo")
	private Modelo id_modelo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData_entrada() {
		return data_entrada;
	}

	public void setData_entrada(Date data_entrada) {
		this.data_entrada = data_entrada;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public double getValor_unitario() {
		return valor_unitario;
	}

	public void setValor_unitario(double valor_unitario) {
		this.valor_unitario = valor_unitario;
	}

	public Ambiente getId_ambiente() {
		return id_ambiente;
	}

	public void setId_ambiente(Ambiente id_ambiente) {
		this.id_ambiente = id_ambiente;
	}

	public Modelo getId_modelo() {
		return id_modelo;
	}

	public void setId_modelo(Modelo id_modelo) {
		this.id_modelo = id_modelo;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data_entrada);
	}

	public String getNome_fantasia() {
		EmpresaDAO daoe = new EmpresaDAO();
		return daoe.buscarId().getNome_fantasia();
	}


	public String getDescricao() {
		return id_modelo.getDescricao();
	}

	public String getComponente() {
		return id_modelo.getComponente();
	}

	public String getMarca() {
		return id_modelo.getMarca();
	}



}
