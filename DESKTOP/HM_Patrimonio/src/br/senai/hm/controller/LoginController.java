package br.senai.hm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import application.Main;
import br.senai.hm.dao.EmpresaDAO;
import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.modelo.Empresa;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.Mensagens;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LoginController implements Initializable {

	private static Stage stage;
	public static Usuario ul;
	private UsuarioDAO daou;
	private EmpresaDAO daoe;
	private Usuario u;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daou = new UsuarioDAO();
		daoe = new EmpresaDAO();
		btnAcessar.setDefaultButton(true);
	}

	@FXML
	private TextField txLogin;
	@FXML
	private PasswordField txSenha;
	@FXML
	private Button btnAcessar;
	@FXML
	private Button btnEsqueci;

	public boolean validarUsuario() {
		u = daou.validarUsuario(txLogin.getText(), txSenha.getText());
		ul = u;
		if (u == null) {
			limparTela();
			return false;
		} else if (u.isStatus() == false) {
			limparTela();
			return false;
		} else {
			return true;
		}

	}

	public boolean validarUsuario2() {
		u = daou.validarUsuario2(txLogin.getText());
		ul = u;
		if (u == null) {
			limparTela();
			return false;
		} else if (u.isStatus() == false) {
			limparTela();
			return false;
		} else {
			return true;
		}

	}

	public void Logar(ActionEvent event) {
		System.out.println("Logar");

		if (validarUsuario()) {
			try {
				stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/views/MenuPrincipal.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setResizable(false);
				stage.setTitle("Principal");
				stage.getIcons().add(new Image("resource/imagens/hm.png"));
				stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					
					@Override
					public void handle(WindowEvent event) {
						System.exit(0);
						Main.getManager().close();
					}
				});
				stage.show();
				Main.getStage().close();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			mostrar();
		}
	}

	public void mostrar() {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Erro");
		alert.setHeaderText(null);
		alert.setContentText("Usuário e senha inválidos.");
		alert.showAndWait();
	}

	public void limparTela() {
		txLogin.clear();
		txSenha.clear();
		u = null;
	}

	public void Esqueci(ActionEvent event) {
		System.out.println("Esquecia a Senha!!!!");
		try {
			enviarEmail();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public void enviarEmail() throws EmailException {

		if (!validarUsuario2()) {
			mostrar();
		} else {
			SimpleEmail email = new SimpleEmail();
			System.out.println("liberando porta...");
			email.setHostName("smtp.live.com");
			email.setStartTLSEnabled(true);
			email.setSmtpPort(587);



			Empresa emp = daoe.buscarId();
			System.out.println(emp.getEmail());

			email.addTo(u.getEmail(), u.getNome());

			email.setFrom(emp.getEmail(), emp.getNome_fantasia());
			// Adicione um assunto
			email.setSubject("Lembrete de senha!");
			// Adicione a mensagem do email
			email.setMsg("Caro usuário do HM Patrimônios" + "\n"
					+ "Recebemos sua solicitação de lembrete de senha, sendo assim estamos a encaminhando." + "\n"
					+ "\n" + "\n" + "Senha: "+ "' "+   u.getSenha()  + " '");
			// Para autenticar no servidor é necessário chamar os dois métodos
			// abaixo
			System.out.println("autenticando...");
			email.setAuthentication(emp.getEmail(), "brasileira2000");
			System.out.println("enviando...");
			try {
				email.send();
				System.out.println("Email enviado!");
				Mensagens.mostrarMsg16();
			} catch (Exception e) {
				Mensagens.mostrarMsg16a();
			}

		}
	}

	public static Stage getStage() {
		return stage;
	}

	public static Usuario getUl() {
		return ul;
	}
}
