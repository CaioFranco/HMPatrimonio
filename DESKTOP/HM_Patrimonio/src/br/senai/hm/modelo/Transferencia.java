package br.senai.hm.modelo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import br.senai.hm.dao.EmpresaDAO;

@Entity
public class Transferencia {


	@Id
	@GeneratedValue
	private Long id;
	private String motivo;
	private Date data;

	private String destino;

	@OneToOne
	@JoinColumn(name = "id_ambiente")
	private Ambiente ambiente;

	@OneToOne
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


	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
	public Long getIdPatrimonio(){
		return patrimonios.getId();
	}

	public String getPatrimonios() {
		return patrimonios.getId_modelo().getComponente();
	}
	public void setPatrimonios(Patrimonio patrimonios) {
		this.patrimonios = patrimonios;
	}

	public String getNome_fantasia() {
		EmpresaDAO daoe = new EmpresaDAO();
		return daoe.buscarId().getNome_fantasia();
	}

	public String getDataFormatada() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}



}
