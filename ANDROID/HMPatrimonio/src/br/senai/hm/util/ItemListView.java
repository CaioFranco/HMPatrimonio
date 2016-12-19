package br.senai.hm.util;

import android.widget.CheckBox;
import br.senai.hm.modelo.Patrimonio;

public class ItemListView {
	
	private String id,modelo,descricao;
	private Patrimonio patrimonio;
	private int check;
	
	public ItemListView() {
		
	}
	
	public ItemListView(String id,String modelo,String descricao,int check,Patrimonio patrimonio) {
		
		this.id = id;
		this.modelo = modelo;
		this.descricao = descricao;
		this.check = check;
		this.patrimonio = patrimonio;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Patrimonio getPatrimonio() {
		return patrimonio;
	}
	public void setPatrimonio(Patrimonio patrimonio) {
		this.patrimonio = patrimonio;
	}

	public int getCheck() {
		return check;
	}

	public void setCheck(int check) {
		this.check = check;
	}

	
	
	
}
