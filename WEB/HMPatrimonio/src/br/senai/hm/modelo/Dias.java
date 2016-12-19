package br.senai.hm.modelo;

public enum Dias {
	UM("1"),TRES("3"),CINCO("5"),SETE("7");

	private String descricao;

		private Dias(String descricao){
			this.descricao = descricao;
		}

		@Override
		public String toString() {
			return this.descricao;
		}


}
