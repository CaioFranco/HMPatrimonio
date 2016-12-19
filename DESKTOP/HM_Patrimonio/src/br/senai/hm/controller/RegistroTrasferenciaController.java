package br.senai.hm.controller;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import br.senai.hm.dao.TransferenciaDAO;
import br.senai.hm.modelo.Transferencia;
import br.senai.hm.util.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class RegistroTrasferenciaController implements Initializable {

	private static final Effect Glow = new Glow(1.0);
	private static final Effect Glow1 = new Glow(0.0);

	private TransferenciaDAO daot;

	@FXML
	private Pane ap;

	@FXML
	private TextField txfOrigem, txfDestino;

	@FXML
	private DatePicker dtpTransferencia;

	@FXML
	private TextArea txMotivo;

	private Transferencia t;

	@FXML
	private ImageView btnBuscarOrigem, btnBuscarDestino;
	@FXML
	private TableView<Transferencia> tbTransferencia;
	@FXML
	private TableColumn<Transferencia, String> columnOrigem;
	@FXML
	private TableColumn<Transferencia, String> columnDestino;
	@FXML
	private TableColumn<Transferencia, String> columnDtTransferencia;
	@FXML
	private TableColumn<Transferencia, String> columnPat;

	private ObservableList<Transferencia> listItens;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		daot = new TransferenciaDAO();
		popularTabelas();
	}

	public void popularTabelas() {
		listItens = FXCollections.observableArrayList(daot.buscarTodos());
		tbTransferencia.getItems().clear();
		columnOrigem.setCellValueFactory(new PropertyValueFactory<>("Ambiente"));
		columnDestino.setCellValueFactory(new PropertyValueFactory<>("Destino"));
		columnDtTransferencia.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Transferencia, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Transferencia, String> param) {
						System.out.println(param.getValue().getDataFormatada());
						return new SimpleStringProperty(param.getValue().getDataFormatada());
					}
				});
		columnPat.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Transferencia,String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<Transferencia, String> param) {

				return new SimpleStringProperty(param.getValue().getPatrimonios());
			}
		});
		tbTransferencia.setItems(listItens);
	}


	public void selectItem(MouseEvent event) {
		if (tbTransferencia.getFocusModel().getFocusedItem() != null) {
			t = tbTransferencia.getFocusModel().getFocusedItem();
			System.out.println("motivo:" + t.getMotivo());
		}
		System.out.println("Entrou aqui!!!");
		popularMotivo();
	}

	private void popularMotivo() {
		 
		if (t != null) {
			txMotivo.setEditable(false);
			txMotivo.setText(t.getMotivo());
			
		} 
	}

	public void buscarOri() {
		if (txfOrigem.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Transferencia> intensEncontrados = FXCollections.observableArrayList();
			for (Transferencia t : listItens) {
				if (t.getAmbiente().getDescricao().toLowerCase().startsWith(txfOrigem.getText().toLowerCase())) {
					intensEncontrados.add(t);
				}
				if (!txfOrigem.equals("")) {
					tbTransferencia.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}

	}

	public void buscarDest() {
		if (txfDestino.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Transferencia> intensEncontrados = FXCollections.observableArrayList();
			for (Transferencia t : listItens) {
				if (t.getDestino().toLowerCase().startsWith(txfDestino.getText().toLowerCase())) {
					intensEncontrados.add(t);
				}
				if (!txfDestino.equals("")) {
					tbTransferencia.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}

	}

	public void btnBuscarOri(MouseEvent event) {
		if (txfOrigem.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Transferencia> intensEncontrados = FXCollections.observableArrayList();
			for (Transferencia t : listItens) {
				if (t.getAmbiente().getDescricao().toLowerCase().startsWith(txfOrigem.getText().toLowerCase())) {
					intensEncontrados.add(t);
				}
				if (!txfOrigem.equals("")) {
					tbTransferencia.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void btnBuscarDest(MouseEvent event) {
		if (txfDestino.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Transferencia> intensEncontrados = FXCollections.observableArrayList();
			for (Transferencia t : listItens) {
				if (t.getDestino().toLowerCase().startsWith(txfDestino.getText().toLowerCase())) {
					intensEncontrados.add(t);
				}
				if (!txfDestino.equals("")) {
					tbTransferencia.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}

	}

	public String getDataFormatada(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}

	public static Date toDate(DatePicker datePicker) {
		if (datePicker.getValue() == null) {
			return null;
		}
		LocalDate ld = datePicker.getValue();
		Instant ins = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(ins);
		return date;
	}

	public void buscarData(ActionEvent event) {
		ObservableList<Transferencia> itensEncontrados = FXCollections.observableArrayList();
		String data = null;
		if (toDate(dtpTransferencia) != null) {
			data = getDataFormatada(toDate(dtpTransferencia));
			System.out.println("No Text: " + data);
			for (Transferencia t : listItens) {

				if (t.getDataFormatada().contains(data)) {
					itensEncontrados.add(t);
				}
			}
			if (!data.equals(null)) {
				tbTransferencia.setItems(itensEncontrados);
			}
		} else {
			popularTabelas();
		}
	}

	public void mudarGlowOrigem(MouseEvent event) {
		btnBuscarOrigem.setEffect(Glow);
	}

	public void mudarGlowDestino(MouseEvent event) {
		btnBuscarDestino.setEffect(Glow);
	}

	public void normalizarOrigem(MouseEvent event) {
		btnBuscarOrigem.setEffect(Glow1);
	}

	public void normalizarDestino(MouseEvent event) {
		btnBuscarDestino.setEffect(Glow1);
	}

}
