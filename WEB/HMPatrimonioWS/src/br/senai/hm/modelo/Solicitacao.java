package br.senai.hm.modelo;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
public class Solicitacao {
	@Id
	@GeneratedValue
	private Long id;
	private String motivo;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Calendar data;
	private boolean resposta;

	@OneToOne
	@JoinColumn(name = "id_ambiente_origem")
	private Ambiente ambiente_origem;
	

	@OneToOne
	@JoinColumn(name = "id_ambiente_destino")
	private Ambiente ambiente_destino;
		
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_patrimonio")
	private List<Patrimonio> patrimonios;


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


	public Calendar getData() {
		return data;
	}


	public void setData(Calendar data) {
		this.data = data;
	}


	public boolean isResposta() {
		return resposta;
	}


	public void setResposta(boolean resposta) {
		this.resposta = resposta;
	}


	public Ambiente getAmbiente_origem() {
		return ambiente_origem;
	}


	public void setAmbiente_origem(Ambiente ambiente_origem) {
		this.ambiente_origem = ambiente_origem;
	}


	public Ambiente getAmbiente_destino() {
		return ambiente_destino;
	}


	public void setAmbiente_destino(Ambiente ambiente_destino) {
		this.ambiente_destino = ambiente_destino;
	}


	public List<Patrimonio> getPatrimonios() {
		return patrimonios;
	}


	public void setPatrimonios(List<Patrimonio> patrimonio) {
		this.patrimonios = patrimonio;
	}
	
	
	
	
}
