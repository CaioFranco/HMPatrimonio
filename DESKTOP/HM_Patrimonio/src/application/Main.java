package application;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.senai.hm.dao.EmpresaDAO;
import br.senai.hm.dao.UsuarioDAO;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {

	private EmpresaDAO daoe;
	private UsuarioDAO daou;
	public static Stage stage;
	private static EntityManager manager;

	@Override
	public void start(Stage stage) throws IOException {

		daoe = new EmpresaDAO();
		daou = new UsuarioDAO();
		if (daoe.buscarId() == null) {
			Parent root = FXMLLoader.load(getClass().getResource("/views/Empresa.fxml"));
			String titulo = "Cadastrar Empresa";
			abrirPainel(root, titulo, stage);
		} else if (daou.buscarAdministrador() == null) {
			Parent root = FXMLLoader.load(getClass().getResource("/views/Usuario.fxml"));
			String titulo = "Cadastar Usuário";
			abrirPainel(root, titulo, stage);
		} else {
			Parent root = FXMLLoader.load(getClass().getResource("/views/Acessar.fxml"));
			String titulo = "Formulário de Acesso";
			abrirPainel(root, titulo, stage);
		}


	}

	public static void main(String[] args) {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("hm_patrimonio");
		manager = factory.createEntityManager();
		System.out.println("passou aqui 2");
		launch(args);

	}

	public void abrirPainel(Parent root, String titulo, Stage stage) {
		try {
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(titulo);
			stage.setResizable(false);
			Main.stage = stage;
			stage.getIcons().add(new Image("resource/imagens/hm.png"));
			

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Stage getStage() {
		return stage;
	}

	public static EntityManager getManager() {
		manager.clear();
		return manager;
	}

}
