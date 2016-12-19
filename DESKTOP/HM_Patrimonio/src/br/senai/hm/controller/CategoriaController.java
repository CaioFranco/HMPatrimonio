package br.senai.hm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.dialog.Dialogs;

import br.senai.hm.dao.CategoriaDAO;
import br.senai.hm.modelo.Categoria;
import br.senai.hm.util.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class CategoriaController implements Initializable {

	private Categoria c;
	private CategoriaDAO daoc;
	private static ObservableList<Categoria> listaCategoria = FXCollections.observableArrayList();

	@FXML
	private AnchorPane ap;
	@FXML
	private TextField txId, txDescricao, txVidaUtil, txDepreciacao;
	@FXML
	private TableView<Categoria> tbCategoria;
	@FXML
	private TableColumn<Categoria, Long> columnId;
	@FXML
	private TableColumn<Categoria, String> columnDesc;
	@FXML
	private TableColumn<Categoria, String> columnVida;
	@FXML
	private TableColumn<Categoria, String> columnDrep;
	private ObservableList<Categoria> listItens;
	final Tooltip tooltip = new Tooltip();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoc = new CategoriaDAO();
		popularTabelas();


	}

	public void popularTabelas() {
		listItens = FXCollections.observableArrayList(daoc.buscarTodos());
		tbCategoria.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDesc.setCellValueFactory(new PropertyValueFactory<>("Descricao"));
		columnVida.setCellValueFactory(new PropertyValueFactory<>("Vida_util"));
		columnDrep.setCellValueFactory(new PropertyValueFactory<>("Depreciacao"));
		tbCategoria.setItems(listItens);
	}

	public void salvar(ActionEvent event) {
		if (c == null) {
			c = new Categoria();
		}

		if (validarCampos() == false) {
			Mensagens.mostrarMsg3();
		} else {
			c.setDescricao(txDescricao.getText().trim());
			c.setDepreciacao(Double.parseDouble(txDepreciacao.getText().trim()));

			c.setVida_util(Integer.parseInt(txVidaUtil.getText().trim()));

			c.setStatus(true);
			if (c.getId() != null) {
				daoc.inserir(c);
				Mensagens.mostrarMsg1();
				limparTela();
			} else {
				daoc.alterar(c);
				Mensagens.mostrarMsg1();
			}
			listaCategoria.add(c);
			popularTabelas();
		}

	}

	public void limparTela() {
		txId.clear();
		txDescricao.clear();
		txDepreciacao.clear();
		txVidaUtil.clear();
		c = null;
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public void novo(ActionEvent event) {
		limparTela();
	}

	public void selectItem(MouseEvent event) {
		if (tbCategoria.getFocusModel().getFocusedItem() != null) {
			c = tbCategoria.getFocusModel().getFocusedItem();
		}
		popularCategoria();
	}

	public void popularCategoria() {
		txId.setText(c.getId().toString());
		txDescricao.setText(c.getDescricao());
		txVidaUtil.setText(c.getVida_util() + "");
		txDepreciacao.setText(c.getDepreciacao() + "");
	}

	public static ObservableList<Categoria> getCategoria() {
		return listaCategoria;

	}

	public boolean validarCampos() {
		if (txDescricao.getText().trim().isEmpty() || txDepreciacao.getText().trim().isEmpty() || txVidaUtil.getText().trim().isEmpty()) {
			return false;
		} else {
			return true;
		}
	}

	public void KeyTyped(KeyEvent event) {
		int limitedType = 4;
		if (!Character.isDigit(event.getCharacter().charAt(0))) {
			event.consume();
		} else if ((txVidaUtil.getText().length() >= limitedType)) {
			txVidaUtil.clear();
			tooltip.setText("Não pode ter mais do que 4 dígitos!");
			txVidaUtil.setTooltip(tooltip);
		}
	}

	public void KeyTyped1(KeyEvent event) {
		int limitedType = 5;
		if (!Character.isDigit(event.getCharacter().charAt(0)) && event.getCharacter().charAt(0) != '.') {
			if (!txDepreciacao.getText().contains(".")) {
				event.consume();
			}

		} else if ((txDepreciacao.getText().length() >= limitedType)) {
			txDepreciacao.clear();
			tooltip.setText("Não pode ter mais do que 4 dígitos!");
			txDepreciacao.setTooltip(tooltip);
		}

	}

}
