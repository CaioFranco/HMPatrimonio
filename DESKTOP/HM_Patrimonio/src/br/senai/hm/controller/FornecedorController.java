package br.senai.hm.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.dialog.Dialogs;

import br.senai.hm.dao.FornecedorDAO;
import br.senai.hm.mask.CnpjMask;
import br.senai.hm.mask.ControllerCnpJMask;
import br.senai.hm.mask.ControllerTelMask;
import br.senai.hm.mask.TelMask;
import br.senai.hm.modelo.Fornecedor;
import br.senai.hm.util.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class FornecedorController implements Initializable {

	private Fornecedor f;
	private FornecedorDAO daof;

	@FXML
	private Pane ap;
	@FXML
	private TextField txCnpj, txNome, txTelefone, txEmail, txBuscarNome, txBuscarCnpj;
	@FXML
	private ImageView btnBuscarNome, btnBuscarCnpj;
	@FXML
	private Button btnSalvar, btnNovo, btnSair;
	@FXML
	private TableView<Fornecedor> tbFornecedor;
	@FXML
	private TableColumn<Fornecedor, Long> columnId;
	@FXML
	private TableColumn<Fornecedor, String> columnCnpj;
	@FXML
	private TableColumn<Fornecedor, String> columnNome;
	@FXML
	private TableColumn<Fornecedor, String> columnTel;
	@FXML
	private TableColumn<Fornecedor, String> columnEmail;
	@FXML
	private ObservableList<Fornecedor> listItens;

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daof = new FornecedorDAO();
		popularTabelas();
		pattern = Pattern.compile(EMAIL_PATTERN);

		txCnpj.focusedProperty().addListener(new CnpjMask(txCnpj));
		txCnpj.setOnKeyReleased(new CnpjMask(txCnpj));
		txCnpj.addEventFilter(KeyEvent.KEY_TYPED, new ControllerCnpJMask());

		txTelefone.focusedProperty().addListener(new TelMask(txTelefone));
		txTelefone.setOnKeyReleased(new TelMask(txTelefone));
		txTelefone.addEventFilter(KeyEvent.KEY_TYPED, new ControllerTelMask());
	}

	public void popularTabelas() {
		listItens = FXCollections.observableArrayList(daof.buscarTodos());
		tbFornecedor.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnCnpj.setCellValueFactory(new PropertyValueFactory<>("Cnpj"));
		columnNome.setCellValueFactory(new PropertyValueFactory<>("Nome"));
		columnTel.setCellValueFactory(new PropertyValueFactory<>("Telefone"));
		columnEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
		tbFornecedor.setItems(listItens);
	}

	public void selectItem(MouseEvent event) {
		if (tbFornecedor.getFocusModel().getFocusedItem() != null) {
			f = tbFornecedor.getFocusModel().getFocusedItem();
		}
		popularFornecedor();
	}

	public void popularFornecedor() {
		txCnpj.setText(f.getCnpj());
		txEmail.setText(f.getEmail());
		txNome.setText(f.getNome());
		txTelefone.setText(f.getTelefone());
	}

	public void salvar(ActionEvent event) {
		if (f == null) {
			f = new Fornecedor();
		}
		if (validarCampos() == false) {
			Mensagens.mostrarMsg3();
		} else if (validarEmail(txEmail.getText().trim()) == false) {
			Mensagens.mostrarMsg2();
		} else {
			f.setCnpj(txCnpj.getText().trim());
			f.setEmail(txEmail.getText().trim());
			f.setNome(txNome.getText().trim());
			f.setTelefone(txTelefone.getText().trim());
			if (f.getId() == null) {
				daof.inserir(f);
				Mensagens.mostrarMsg1();
				limparTela();
				popularTabelas();
			} else {
				daof.alterar(f);
				Mensagens.mostrarMsg1();
			}

		}

	}

	public void limparTela() {
		txCnpj.clear();
		txNome.clear();
		txTelefone.clear();
		txEmail.clear();
		f = null;
	}

	public void buscarNome(ActionEvent event) {
		if (txBuscarNome.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Fornecedor> itensEncontrados = FXCollections.observableArrayList();
			for (Fornecedor f : listItens) {
				if (f.getNome().toLowerCase().contains(txBuscarNome.getText().toLowerCase())) {
					itensEncontrados.add(f);
				}
			}
			if (!txBuscarNome.equals("")) {
				tbFornecedor.setItems(itensEncontrados);
			} else {
				popularTabelas();
			}
		}

	}

	public void btnbuscarNome(MouseEvent event){
		if (txBuscarNome.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Fornecedor> itensEncontrados = FXCollections.observableArrayList();
			for (Fornecedor f : listItens) {
				if (f.getNome().toLowerCase().contains(txBuscarNome.getText().toLowerCase())) {
					itensEncontrados.add(f);
				}
			}
			if (!txBuscarNome.equals("")) {
				tbFornecedor.setItems(itensEncontrados);
			} else {
				popularTabelas();
			}
		}
	}

	public void buscarCnpj(ActionEvent event) {
		if (txBuscarCnpj.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		}else {
			ObservableList<Fornecedor> itensEncontrados = FXCollections.observableArrayList();
			for (Fornecedor f : listItens) {
				if (f.getCnpj().contains(txBuscarCnpj.getText())) {
					itensEncontrados.add(f);
				}
			}
			if (!txBuscarCnpj.equals("")) {
				tbFornecedor.setItems(itensEncontrados);
			} else {
				popularTabelas();
			}
		}
	}

	public void btnbuscarCNPJ(MouseEvent event){
		if (txBuscarCnpj.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
		}else {
			ObservableList<Fornecedor> itensEncontrados = FXCollections.observableArrayList();
			for (Fornecedor f : listItens) {
				if (f.getCnpj().contains(txBuscarCnpj.getText())) {
					itensEncontrados.add(f);
				}
			}
			if (!txBuscarCnpj.equals("")) {
				tbFornecedor.setItems(itensEncontrados);
			} else {
				popularTabelas();
			}
		}
	}

	public void novo(ActionEvent event) {
		limparTela();
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public boolean validarEmail(String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public boolean validarCampos() {
		if (txCnpj.getText().trim().isEmpty() || txNome.getText().trim().isEmpty() || txTelefone.getText().trim().isEmpty()) {
			return false;
		} else {
			return true;
		}

	}
}
