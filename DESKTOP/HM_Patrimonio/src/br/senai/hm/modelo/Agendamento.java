package br.senai.hm.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

import br.senai.hm.dao.EmpresaDAO;


@Entity
public class Agendamento {
	@Id
	@GeneratedValue
	private Long id;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date data;
	private Dias limite;

	@OneToOne
	@JoinColumn(name = "id_ambiente")
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
	
	public String getNome_fantasia() {
		EmpresaDAO daoe = new EmpresaDAO();
		return daoe.buscarId().getNome_fantasia();
	}

}
