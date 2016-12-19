package br.senai.hm.controller;

import java.net.URL;
import java.util.ResourceBundle;

import antlr.collections.List;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.dao.TransferenciaDAO;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Transferencia;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

public class DetalheTransferenciaController implements Initializable {
	@FXML
	private TableView<Transferencia> tbTransf;
	@FXML
	private TableColumn<Patrimonio, String> columnPat;
	@FXML
	private TableColumn<Patrimonio, String> columnDest;

	private TransferenciaDAO daoT;

	private PatrimonioDAO daoP;

	private ObservableList<Transferencia> listItens;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoT = new TransferenciaDAO();
		daoP = new PatrimonioDAO();
		popularTabela();
	}

	public void popularTabela() {
		listItens = FXCollections.observableArrayList(daoT.buscarTodos());
		tbTransf.getItems().clear();
		columnPat.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getDescricao());
					}
				});
		tbTransf.setItems(listItens);
	}



}
