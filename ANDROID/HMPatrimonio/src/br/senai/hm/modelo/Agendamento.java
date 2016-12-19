package br.senai.hm.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Agendamento implements Serializable {

	private Long id;
	private Date data;
	private Dias limite;
	private Ambiente ambiente;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date date) {
		this.data = date;
	}

	public Dias getLimite() {
		return limite;
	}

	public void setLimite(Dias limite) {
		this.limite = limite;

	}

	public Ambiente getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(Ambiente ambiente) {
		this.ambiente = ambiente;
	}
	
	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Data do Agendamento: "+getDataFormatada();
	}


}
