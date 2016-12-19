package br.senai.hm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Patrimonio;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class DetalheConferenciaController implements Initializable {

	@FXML
	private Label lbAmbiente,lbQtd,lbQtdPresente,lbQtdAusente;
	@FXML
	private TextField txAgendamento,txRealizacao;
	@FXML
	private TextArea txMotivo;
	@FXML
	private RadioButton radioPresente,radioAusente,radioTodos;
	@FXML
	private TableView<Patrimonio> tbPatrimonio;
	@FXML
	private TableColumn<Patrimonio, Long> columnId;
	@FXML
	private TableColumn<Patrimonio, String> columnDesc;
	@FXML
	private TableColumn<Patrimonio, String> columnModelo;
	@FXML
	private TableColumn<Patrimonio, String> columnMarca;
	@FXML
	private TableColumn<Patrimonio, String> columnStatus;

	private ObservableList<Patrimonio> listItens;
	ObservableList<Patrimonio> listAusentes;
	private ToggleGroup grupo = new ToggleGroup();
	private ConferenciaDAO daoc;
	private PatrimonioDAO daop;
	private Conferencia c;

	public DetalheConferenciaController() {
		c = ConferenciaController.getConferencia();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoc = new ConferenciaDAO();
		daop = new PatrimonioDAO();
		radioAusente.setToggleGroup(grupo);
		radioPresente.setToggleGroup(grupo);
		radioTodos.setToggleGroup(grupo);
		lbAmbiente.setText(c.getAgendamento().getAmbiente().toString());
		txAgendamento.setText(c.getAgendamento().getDataFormatada());
		txRealizacao.setText(c.getDataFormatada());
		popularTabelas();
	}

	public void popularTabelas(){
		listItens = FXCollections.observableArrayList(listaPatrimonio());
		listAusentes = FXCollections.observableArrayList(c.getPatrimonio_ausente());
		tbPatrimonio.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDesc.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patrimonio,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
				return new SimpleStringProperty(param.getValue().getId_modelo().getDescricao());
			}
		});
		columnModelo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patrimonio,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
				return new SimpleStringProperty(param.getValue().getId_modelo().getComponente());
			}
		});
		columnMarca.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patrimonio,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
				return new SimpleStringProperty(param.getValue().getId_modelo().getMarca());
			}
		});
		columnStatus.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Patrimonio,String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
				int tem = 0;
				for (Patrimonio p : listAusentes) {
					if (param.getValue().getId() == p.getId()) {
						tem = 1;
					}
				}
				if (tem == 1) {
					return new SimpleStringProperty("Ausente");
				}
				else{
					return new SimpleStringProperty("Presente");
				}
			}
		});
		tbPatrimonio.setItems(listItens);
		lbQtdAusente.setText(listAusentes.size()+"");
		lbQtdPresente.setText((listItens.size()-listAusentes.size())+"");
	}

	private ObservableList<Patrimonio> listaPatrimonio(){
		ObservableList<Patrimonio> patrimoniosEncontrados = FXCollections.observableArrayList();
		int qtd = 0;
		for (Patrimonio p : daop.buscarTodos()) {
			if (c.getAgendamento().getAmbiente().getId().equals(p.getId_ambiente().getId())) {
				patrimoniosEncontrados.add(p);
				qtd++;
			}
		}
		lbQtd.setText(patrimoniosEncontrados.size()+"");
		return patrimoniosEncontrados;
	}

	public void buscarPresente(ActionEvent event){
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList(listItens);
		itensEncontrados.removeAll(listAusentes);
		if (radioPresente.isSelected()) {
			tbPatrimonio.setItems(itensEncontrados);
		}else{
			popularTabelas();
		}
	}

	public void buscarAusente(ActionEvent event){
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList(listAusentes);
		if (radioAusente.isSelected()) {
			tbPatrimonio.setItems(itensEncontrados);
		}else{
			popularTabelas();
		}
	}

	public void buscarTodos(ActionEvent event){
		popularTabelas();
	}


}
