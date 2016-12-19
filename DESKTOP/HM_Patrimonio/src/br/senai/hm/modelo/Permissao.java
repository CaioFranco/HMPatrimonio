package br.senai.hm.modelo;

public enum Permissao {

	ADMINISTRADOR("Administrador"), GESTOR("Gestor");

	private String descricao;

	private Permissao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return this.descricao;
	}

}
