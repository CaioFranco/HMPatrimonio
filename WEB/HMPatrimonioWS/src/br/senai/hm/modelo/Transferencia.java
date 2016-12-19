package br.senai.hm.modelo;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import javafx.stage.Stage;


@Entity
public class Transferencia {


	@Id
	@GeneratedValue
	private Long id;
	private String motivo;
	private LocalDate data;
	private String destino;

	@ManyToOne
	@JoinColumn(name = "id_ambiente")
	private Ambiente ambiente;

	@ManyToOne
	@JoinColumn(name = "id_patrimonio")
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
	public LocalDate getData() {
		return data;
	}
	public void setData(LocalDate localDate) {
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
	public Patrimonio getPatrimonios() {
		return patrimonios;
	}
	public void setPatrimonios(Patrimonio patrimonios) {
		this.patrimonios = patrimonios;
	}


	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}



}
