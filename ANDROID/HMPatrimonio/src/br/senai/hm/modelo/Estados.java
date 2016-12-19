package br.senai.hm.modelo;

public enum Estados{
	ACRE("Acre"), ALAGOAS("Alagoas"), AMAPA("Ampá"), AMAZONAS("Amazonas"), BAHIA("Bahia"),
	CEARA("Ceará"), DISTRITO("Distrito Federal"),
	ESPITIROSANTO("Espítiro Santo"), GOIAS("Goiás"), MATOGROSSO("Mato Grosso"),
	MATOGROSSOSUL("Mato Grosso do Sul"), MINASGERAIS("Mina Gerais"),
	PARA("Pará"), PARAIBA("Paraíba"), PARANA("Paraná"), PERNAMBUCO("Pernambuco"),
	PIAUI("Piauí"), RIOJANEIRO("Rio de Janeiro"), RGNORTE("Rio Grande do Norte"),
	RGSUL("Rio Grande do Sul"), RONDONIA("Rondonia"), RORAIMA("Roraima"),
	SANTACATARINA("Santa Catarina"), SAOPAULO("São Paulo"), SERGIPE("Sergipe"),
	TOCANTINS("Tocantins");

	private String descricao;

	private Estados(String descricao){
		this.descricao = descricao;
	}

	@Override
	public String toString() {

		return this.descricao;
	}

}

