package br.senai.hm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.PersistenceException;
import javax.validation.ConstraintViolationException;

import application.LoginDuplicadoException;
import application.Main;
import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Usuario;
import br.senai.hm.util.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class UsuarioController implements Initializable {

	@FXML
	private ComboBox<Permissao> cmbPermissao = new ComboBox<>(FXCollections.observableArrayList(Permissao.values()));
	private Stage stage;
	private static final Effect Glow = new Glow(1.0);
	private static final Effect Glow1 = new Glow(0.0);
	private Usuario u;
	private UsuarioDAO daou;

	@FXML
	private Pane ap;

	@FXML
	private TextField txfId;

	@FXML
	private TextField txfNome;

	@FXML
	private TextField txfEmail;

	@FXML
	private TextField txfLogin;

	@FXML
	private TextField txfSenha;

	@FXML
	private TextField txfConfSenha;

	@FXML
	private TextField txfBuscar;

	@FXML
	private Button btnNovo;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnSair;

	@FXML
	private Button btnInativar;

	@FXML
	private ImageView btnBuscar;

	@FXML
	private TableView<Usuario> tbUsuario;

	@FXML
	private TableColumn<Usuario, Long> columnId;

	@FXML
	private TableColumn<Usuario, String> columnNome;

	@FXML
	private TableColumn<Usuario, String> columnPermissao;

	@FXML
	private TableColumn<Usuario, String> columnLogin;

	@FXML
	private TableColumn<Usuario, String> columnStatus;

	private static ObservableList<Usuario> listItens;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;

	public UsuarioController() {
		stage = Main.getStage();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daou = new UsuarioDAO();
		cmbPermissao.setItems(FXCollections.observableArrayList(Permissao.values()));
		popularTabela();
		pattern = Pattern.compile(EMAIL_PATTERN);
	}

	public void popularTabela() {
		listItens = FXCollections.observableArrayList(daou.buscarTodos());
		tbUsuario.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		columnPermissao.setCellValueFactory(new PropertyValueFactory<>("Permissao"));
		columnLogin.setCellValueFactory(new PropertyValueFactory<>("Login"));
		columnStatus.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Usuario, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Usuario, String> param) {
						if (param.getValue().isStatus()) {
							return new SimpleStringProperty("Ativo");
						} else {
							return new SimpleStringProperty("Inativo");
						}
					}
				});
		tbUsuario.setItems(listItens);
	}

	public void selectItem() {
		if (tbUsuario.getFocusModel().getFocusedItem() != null) {
			u = tbUsuario.getFocusModel().getFocusedItem();
		}
		popularUsuario();
	}

	public void popularUsuario() {
		txfId.setText(u.getId().toString());
		txfNome.setText(u.getNome().toString());
		cmbPermissao.setValue(u.getPermissao());
		txfEmail.setText(u.getEmail().toString());
		txfLogin.setText(u.getLogin().toString());
		txfSenha.setText(u.getSenha().toString());
		txfConfSenha.setText(u.getSenha().toString());
	}

	public void salvar(ActionEvent event) {

		if (u == null) {
			u = new Usuario();
			u.setStatus(true);
		}

		if (validarCampos() == false) {
			Mensagens.mostrarMsg3();
		} else if (validarEmail(txfEmail.getText().trim()) == false) {
			Mensagens.mostrarMsg2();
		} else {
			try {
				u.setNome(txfNome.getText().trim());
				u.setEmail(txfEmail.getText().trim());
				u.setLogin(txfLogin.getText().trim());
				u.setSenha(txfSenha.getText().trim());
				u.setPermissao(cmbPermissao.getValue());

				if (u.getId() == null) {
					if (txfConfSenha.getText().equals(txfSenha.getText())) {
						// System.out.println("1 " + u.getId());
						daou.inserir(u);
						Mensagens.mostrarMsg1();
						limparTela();

						if (daou.buscarTodos().size() == 1) {
							// System.out.println(u.getId());
							sair(event);
							try {
								Parent root;
								root = FXMLLoader.load(getClass().getResource("/views/Acessar.fxml"));
								String titulo = "Formulário de Acesso";
								abrirPainel(root, titulo, stage);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					} else {
						Mensagens.mostrarMsg0();
					}

				} else if (u.isStatus()) {
					System.out.println(u.isStatus());
					if (txfConfSenha.getText().equals(txfSenha.getText())) {

							daou.alterar(u);
							System.out.println("alterou");
							Mensagens.mostrarMsg1();


					} else {
						Mensagens.mostrarMsg0();
					}
				} else {
					Mensagens.mostrarMsg7();
				}
				popularTabela();
			} catch (LoginDuplicadoException e) {
				Mensagens.mostrarMsg13();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	public void abrirPainel(Parent root, String titulo, Stage stage) {
		try {
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(titulo);
			Main.stage = stage;
			stage.getIcons().add(new Image("resource/imagens/hm.png"));
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					System.exit(0);	
					Main.getManager().close();
				}
			});
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void buscar(ActionEvent event) {
		if (txfBuscar.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabela();
		} else {
			ObservableList<Usuario> intensEncontrados = FXCollections.observableArrayList();
			for (Usuario u : listItens) {
				if (u.getNome().toLowerCase().startsWith(txfBuscar.getText().toLowerCase())) {
					intensEncontrados.add(u);
				}
				if (!txfBuscar.equals("")) {
					tbUsuario.setItems(intensEncontrados);
				} else {
					popularTabela();
				}
			}
		}

	}

	public void buscarNome(MouseEvent event) {
		if (txfBuscar.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabela();
		} else {
			ObservableList<Usuario> intensEncontrados = FXCollections.observableArrayList();
			for (Usuario u : listItens) {
				if (u.getNome().toLowerCase().startsWith(txfBuscar.getText().toLowerCase())) {
					intensEncontrados.add(u);
				}
				if (!txfBuscar.equals("")) {
					tbUsuario.setItems(intensEncontrados);
				} else {
					popularTabela();
				}
			}
		}
	}

	public void mudarGlow(MouseEvent event) {
		btnBuscar.setEffect(Glow);
	}

	public void normalizar(MouseEvent event) {
		btnBuscar.setEffect(Glow1);
	}

	public void novo() {
		limparTela();
	}

	public void limparTela() {
		txfId.clear();
		txfNome.clear();
		txfEmail.clear();
		txfLogin.clear();
		txfSenha.clear();
		txfConfSenha.clear();
		cmbPermissao.setValue(null);
		cmbPermissao.setItems(FXCollections.observableArrayList(Permissao.values()));
		u = null;
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public void inativar(ActionEvent event) {
		if (tbUsuario.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg6();
		} else if (u.isStatus() == false) {
			Mensagens.mostrarMsg7();
		} else if (tbUsuario.getFocusModel().getFocusedItem() == LoginController.ul) {
			Mensagens.mostrarMsg7();
		} else if (Mensagens.mostrarMsg5() == true) {
			daou.inativar(u);
			popularTabela();
		}

	}

	public boolean validarEmail(String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public boolean validarCampos() {
		if (txfNome.getText().trim().isEmpty() || txfLogin.getText().trim().isEmpty() || txfSenha.getText().trim().isEmpty()
				|| txfConfSenha.getText().trim().isEmpty() || cmbPermissao.getValue() == null) {
			return false;

		} else {
			return true;
		}
	}
}
