package br.senai.hm.modelo;

public enum Situacao{
	ND(""),NOVO("Novo"),SEMINOVO("Seminovo");
	
private String descricao;
	
	private Situacao(String descricao){
		this.descricao = descricao;
	}

	@Override
	public String toString() {	
		return descricao;
	}
	

	
}
