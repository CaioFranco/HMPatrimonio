package br.senai.hm.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Mensagens {

	public Mensagens() {
		super();
	}

	public static void mostrarMsg0() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Senha e Confirmação de Senha não conferem.");
		alert.showAndWait();
	}

	public static void mostrarMsg1() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Sucesso");
		alert.setHeaderText(null);
		alert.setContentText("Registro salvo com sucesso.");
		alert.showAndWait();
	}

	public static void mostrarMsg2() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Verifique os dados");
		alert.setHeaderText(null);
		alert.setContentText("Email inválido.");
		alert.showAndWait();
	}

	public static void mostrarMsg3() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Verifique os dados");
		alert.setHeaderText(null);
		alert.setContentText("Existem campos em braco que devem ser preeenchidos.");
		alert.showAndWait();
	}

	public static void mostrarMsg4() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Pesquisa");
		alert.setHeaderText(null);
		alert.setContentText("Informe dados para realizar a pesquisa.");
		alert.showAndWait();
	}

	public static boolean mostrarMsg5() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmação");
		alert.setHeaderText(null);
		alert.setContentText(
				"Deseja realmente inativar o registro selecionado? A alteração realizada será permanente!");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}

	public static boolean mostrarMsg5a() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmação");
		alert.setHeaderText(null);
		alert.setContentText(
				"Deseja realmente inativar o registro selecionado?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}
	public static void mostrarMsg6() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Selecione registro");
		alert.setHeaderText(null);
		alert.setContentText("Nenhum registro selecionado, por favor, selecione um registro e tente novamente.");
		alert.showAndWait();
	}

	public static void mostrarMsg7() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Registro inválido");
		alert.setHeaderText(null);
		alert.setContentText("O registro selecionado não pode ser alterado.");
		alert.showAndWait();
	}

	public static void mostrarMsg8() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Não existe registro na lista");
		alert.setHeaderText(null);
		alert.setContentText("Não existe patriônio nesse ambiente");
		alert.showAndWait();
	}

	public static void mostrarMsg9() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Não foi possível realizar operação, verique todas as informações e tente novamente.");
		alert.showAndWait();
	}

	public static void mostrarMsg10() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Não foi selecionado nenhum patrimônio.");
		alert.showAndWait();
	}

	public static boolean mostrarMsg11() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmação");
		alert.setHeaderText(null);
		alert.setContentText(
				"Só é possível inativar um patrimônio em situação de transferência. Deseja transferir um patrimônio?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}

	public static boolean mostrarMsg12() {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Atenção");
		alert.setHeaderText(null);
		alert.setContentText(
				"Se este patrimônio está sendo ativado, significa que ele retornou de uma transferência, para proseguir clique em"+" OK"+".");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			return true;
		}
		return false;
	}

	public static void mostrarMsg13() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Não foi possível registrar este usuário, o login desejado já está atribuido, por favor tente outro!");
		alert.showAndWait();
	}

	public static void mostrarMsg14() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro em operação");
		alert.setHeaderText("Não foi possível agendar a conferência");
		alert.setContentText("A data de agendamento não pode ser anterior à data atual!");
		alert.showAndWait();
	}

	public static void mostrarMsg14a() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro em operação");
		alert.setHeaderText("Não foi possível cadastrar o patrimônio");
		alert.setContentText("A data de cadastro não pode ser posterior à data atual!");
		alert.showAndWait();
	}

	public static void mostrarMsg15() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Erro em operação");
		alert.setHeaderText("Não foi possível agendar a conferência");
		alert.setContentText("Esse ambiente possui uma conferência pendente.");
		alert.showAndWait();
	}

	public static void mostrarMsg16() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Lembre enviado");
		alert.setHeaderText(null);
		alert.setContentText("O lembrete de senha enviado, verifique seu email.");
		alert.showAndWait();
}


	public static void mostrarMsg16a() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Falha ao enviar E-mail, por favor entre em contato com o admnistrador do banco de dados e solicite sua senha.");
		alert.showAndWait();
	}

	public static void mostrarMsg17() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Não foi posível registrar");
		alert.setHeaderText(null);
		alert.setContentText("O gestor desejado já gerencia outro ambiente, sendo assim, escolha outro.");
		alert.showAndWait();
	}

	public static void mostrarMsg18() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Não foi possível exibir");
		alert.setHeaderText(null);
		alert.setContentText("A conferência selelionada ainda não foi realizada.");
		alert.showAndWait();
	}
	public static void mostrarMsg19() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Falha ao cadastrar os patrimônios, por favor verifique se há N.I. vagos.");
		alert.showAndWait();
	}
	public static void mostrarMsg20() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro em operação");
		alert.setHeaderText(null);
		alert.setContentText("Falha ao cadastrar os patrimônios, Quantidade não pode ser 0.");
		alert.showAndWait();
	}

}
