package br.senai.hm.modelo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Conferencia {
	@Id
	@GeneratedValue
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;

	@OneToOne
	@JoinColumn(name = "id_agendamento")
	private Agendamento agendamento;

	@ManyToMany(fetch=FetchType.EAGER)
	@JoinColumn(name = "patrimonio_ausente")
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

	/*public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}*/

	public boolean isStatus() {
		return false;
	}
	
}
