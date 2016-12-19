package br.senai.hm.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.BreadCrumbBar.BreadCrumbActionEvent;
import org.controlsfx.dialog.Dialogs;

import com.sun.org.apache.bcel.internal.generic.GETSTATIC;

import application.Main;
import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.modelo.Ambiente;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn.CellDataFeatures;
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

public class AmbienteController implements Initializable {

	private static final Effect Glow = new Glow(1.0);
	private static final Effect Glow1 = new Glow(0.0);

	private Ambiente a;
	private AmbienteDAO daoa;
	private UsuarioDAO daou;

	@FXML
	private TextField txfDescricao, txfBuscar;
	@FXML
	private Pane ap;
	@FXML
	private ComboBox<Usuario> cmbGestor;
	@FXML
	private Button btnGestor;
	@FXML
	private ImageView btnBuscarDesc;
	@FXML
	private Button btnSalvar;
	@FXML
	private Button btnSair;
	@FXML
	private Button btnNovo;
	@FXML
	private Button btnInativar;

	@FXML
	private TableView<Ambiente> tbAmbiente;

	@FXML
	private TableColumn<Ambiente, Long> columnId;

	@FXML
	private TableColumn<Ambiente, String> columnDescricao;

	@FXML
	private TableColumn<Ambiente, String> columnGestor;

	@FXML
	private TableColumn<Ambiente, String> columnStatus;

	private static ObservableList<Ambiente> listItens;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		daoa = new AmbienteDAO();
		daou = new UsuarioDAO();
		popularComboBox();
		popularTabela();
	}

	public void popularComboBox() {
		ObservableList<Usuario> listUsuarios = FXCollections.observableArrayList();
		for (Usuario u : daou.buscarTodos()) {
			if (u.getPermissao() == Permissao.GESTOR && u.isStatus() != false) {
				listUsuarios.add(u);
			}
		}

		cmbGestor.setItems(listUsuarios);

	}

	public void popularTabela() {
		listItens = FXCollections.observableArrayList(daoa.buscarTodos());
		tbAmbiente.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDescricao.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
		columnGestor.setCellValueFactory(new PropertyValueFactory<>("Usuario"));
		columnStatus.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Ambiente, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Ambiente, String> param) {
						if (param.getValue().isStatus()) {
							return new SimpleStringProperty("Ativo");
						} else {
							return new SimpleStringProperty("Inativo");
						}
					}
				});
		tbAmbiente.setItems(listItens);
	}

	public void selectItem(MouseEvent event) {
		if (tbAmbiente.getFocusModel().getFocusedItem() != null) {
			a = tbAmbiente.getFocusModel().getFocusedItem();
			popularAmbiente();
		}
	}

	private void popularAmbiente() {
		txfDescricao.setText(a.getDescricao().toString());
		cmbGestor.setValue(a.getUsuario());
	}

	public void addGestor(ActionEvent event) {

		try {
			Stage stage = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/views/Usuario.fxml"));
			Scene scene = new Scene(root);
			stage.getIcons().add(new Image("resource/imagens/hm.png"));
			stage.setScene(scene);
			stage.show();
			Main.getStage().close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(ActionEvent event) {
		if (a == null) {
			a = new Ambiente();
		}

		if (txfDescricao.getText().trim().isEmpty() || cmbGestor.getValue() == null) {
			Mensagens.mostrarMsg3();
		} else {

			Usuario u = cmbGestor.getValue();
			if (validarGestor(u)) {
			Mensagens.mostrarMsg17();
			} else {
				a.setDescricao(txfDescricao.getText().trim());
				a.setUsuario(cmbGestor.getValue());
				a.setStatus(true);
				if (a.getId() == null) {

					System.out.println("nao salvou");

					daoa.inserir(a);
					System.out.println("1 " + a.getId());
					System.out.println("salvou");
					Mensagens.mostrarMsg1();
					limparTela();

				} else {
					System.out.println("2 " + a.getId());
					System.out.println("nao  salvou alterando");
					System.out.println("alterou");
					daoa.alterar(a);
					Mensagens.mostrarMsg1();

				}
			}

			popularTabela();
		}

	}

	private boolean validarGestor(Usuario u) {
		for (Ambiente ambiente : listItens) {
			if (ambiente.isStatus()) {
				if (ambiente.getUsuario().getId().equals(u.getId())) {
					return true;
				}
			}

		}
		return false;
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public void novo(ActionEvent event) {
		limparTela();
	}

	public void limparTela() {
		txfBuscar.clear();
		txfDescricao.clear();
		cmbGestor.setValue(null);
		// popularComboBox();
		a = null;
	}

	public void inativar(ActionEvent event) {
		if (tbAmbiente.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg6();
		} else if (a.isStatus() == false) {
			Mensagens.mostrarMsg7();
		} else if (Mensagens.mostrarMsg5a() == true) {
			daoa.inativar(a);
			popularTabela();
		}

	}

	public void buscarDescricao(MouseEvent event) {
		if (txfBuscar.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabela();
		} else {
			ObservableList<Ambiente> intensEncontrados = FXCollections.observableArrayList();
			for (Ambiente a : listItens) {
				if (a.getDescricao().toLowerCase().startsWith(txfBuscar.getText().toLowerCase())) {
					intensEncontrados.add(a);
				}
				if (!txfBuscar.equals("")) {
					tbAmbiente.setItems(intensEncontrados);
				} else {
					popularTabela();
				}
			}
		}
	}

	public void buscar() {
		if (txfBuscar.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabela();
		} else {
			ObservableList<Ambiente> intensEncontrados = FXCollections.observableArrayList();
			for (Ambiente a : listItens) {
				if (a.getDescricao().toLowerCase().startsWith(txfBuscar.getText().toLowerCase())) {
					intensEncontrados.add(a);
				}
				if (!txfBuscar.equals("")) {
					tbAmbiente.setItems(intensEncontrados);
				} else {
					popularTabela();
				}
			}
		}

	}

	public void atualizarAmbiente(MouseEvent event) {
		popularComboBox();
	}

	public void mudarGlow(MouseEvent event) {
		btnBuscarDesc.setEffect(Glow);
	}

	public void normalizar(MouseEvent event) {
		btnBuscarDesc.setEffect(Glow1);
	}

}
