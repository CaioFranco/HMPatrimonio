package br.senai.hm.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Patrimonio implements Serializable {

	private Long id;
	private Date data_entrada;
	private Situacao situacao;
	private double valor_unitario;
	private boolean status;
	private Ambiente id_ambiente;
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
	
	public String getDescricao() {
		return id_modelo.getDescricao();
	}
	
	public String getComponente() {
		return id_modelo.getComponente();
	}

	public String getMarca() {
		return id_modelo.getMarca();
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return id.toString();
	}



}
