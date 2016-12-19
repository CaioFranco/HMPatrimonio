package br.senai.hm.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.util.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class ConferenciaController implements Initializable {
	private static final Effect Glow = new Glow(1.0);
	private static final Effect Glow1 = new Glow(0.0);

	private static Conferencia c;
	
	private ConferenciaDAO daoc;

	@FXML
	private Pane ap;

	@FXML
	private RadioButton radioSim, radioNao, radioTodas;

	@FXML
	private ToggleGroup grupo = new ToggleGroup();

	@FXML
	private TextField dtpAtual;

	@FXML
	private TableView<Conferencia> tbConferencia;

	@FXML
	private TableColumn<Conferencia, Long> columnId;

	@FXML
	private TableColumn<Conferencia, String> columnDtAgendamento;

	@FXML
	private TableColumn<Conferencia, String> columnAmbiente;

	@FXML
	private TableColumn<Conferencia, String> columnInconsistencias;

	@FXML
	private TableColumn<Conferencia, String> columnStatus;

	private static ObservableList<Conferencia> listItens;

	@FXML
	private Button btnDetalhar;

	@FXML
	private Button btnSair;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		daoc = new ConferenciaDAO();
		popularTabela();
		radioSim.setToggleGroup(grupo);
		radioNao.setToggleGroup(grupo);
		radioTodas.setToggleGroup(grupo);

	}

	Date dtAtual = new Date(System.currentTimeMillis());
	String dtf = "dd/MM/yyyy";
	SimpleDateFormat formatas = new SimpleDateFormat(dtf);
	String data = formatas.format(dtAtual);

	private void popularTabela() {

		dtpAtual.setText(data);

		listItens = FXCollections.observableArrayList(daoc.buscarTodos());
		tbConferencia.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDtAgendamento.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Conferencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Conferencia, String> param) {
						return new SimpleStringProperty(param.getValue().getAgendamento().getDataFormatada());
					}
				});

		columnAmbiente.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Conferencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Conferencia, String> param) {

						return new SimpleStringProperty(param.getValue().getAgendamento().getAmbiente().getDescricao());
					}
				});

		columnInconsistencias.setCellValueFactory(new PropertyValueFactory<>("Inconsistencia"));

		columnStatus.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Conferencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Conferencia, String> param) {
						if (param.getValue().getData() != null) {
							return new SimpleStringProperty("Realizada");
						} else {
							return new SimpleStringProperty("Pendente");
						}
					}
				});
		tbConferencia.setItems(listItens);
	}

	public void detalhar(ActionEvent event) {
		if (tbConferencia.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg6();
		} else if (c != null && c.getData() != null ) {
			try {
				Stage stage = new Stage();
				Parent root = FXMLLoader.load(getClass().getResource("/views/Detalhe_Conferencia.fxml"));
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.setTitle("Detalhe de Conferencia");
				stage.getIcons().add(new Image("resource/imagens/hm.png"));
				stage.show();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Mensagens.mostrarMsg18();
		}

	}

	public void buscarSim() {
		ObservableList<Conferencia> itensEncontrados = FXCollections.observableArrayList();
		for (Conferencia c : listItens) {
			if (c.getData() != null) {
				itensEncontrados.add(c);
			}
		}
		if (radioSim.isSelected()) {
			tbConferencia.setItems(itensEncontrados);
		}
	}

	public void buscarNao() {
		ObservableList<Conferencia> itensEncontrados = FXCollections.observableArrayList();
		for (Conferencia c : listItens) {
			if (c.getData() == null) {
				itensEncontrados.add(c);
			}
		}
		if (radioNao.isSelected()) {
			tbConferencia.setItems(itensEncontrados);
		}
	}

	public void buscarTodas(ActionEvent e) {
		popularTabela();
	}

	public void selectItem(MouseEvent event) {
		if (tbConferencia.getFocusModel().getFocusedItem() != null) {
			c = tbConferencia.getFocusModel().getFocusedItem();
		}

	}

	public static Conferencia getConferencia() {
		return c;
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}
}
