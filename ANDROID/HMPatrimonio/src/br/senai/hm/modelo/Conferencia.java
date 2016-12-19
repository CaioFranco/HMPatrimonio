package br.senai.hm.modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Conferencia implements Serializable {

	private Long id;
	private Date data;
	private Agendamento agendamento;
	private List<Patrimonio> patrimonio_ausente;

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

	public Agendamento getAgendamento() {
		return agendamento;
	}

	public void setAgendamento(Agendamento agendamento) {
		this.agendamento = agendamento;
	}

	public List<Patrimonio> getPatrimonio_ausente() {
		return patrimonio_ausente;
	}

	public void setPatrimonio_ausente(List<Patrimonio> patrimonio_ausente) {
		this.patrimonio_ausente = patrimonio_ausente;
	}

	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		if (data != null) {
			return sdf.format(data);
		}
		return "";
	}

	public boolean isStatus() {
		return false;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Data da Conferência: "+getDataFormatada();
	}

}
