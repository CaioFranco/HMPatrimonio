package br.senai.hm.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Transferencia implements Serializable {

	private Long id;
	private String motivo;
	private Date data;
	private String destino;
	private Ambiente ambiente;
	private Patrimonio patrimonios;


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
	
	public Date getLocalData() {
		return data;
	}
	
	public Date getData() {
		return new Date();
	}
	
	public void setData(Date localDate) {
		this.data = localDate;
	}
	
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Ambiente getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	public String getPatrimonios() {
		return patrimonios.getId_modelo().getDescricao();
	}
	public void setPatrimonios(Patrimonio patrimonios) {
		this.patrimonios = patrimonios;
	}
	
	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}

}
