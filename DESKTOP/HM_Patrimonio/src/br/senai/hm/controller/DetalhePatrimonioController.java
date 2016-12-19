package br.senai.hm.controller;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.poi.hssf.record.formula.functions.T;

import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.dao.TransferenciaDAO;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Situacao;
import br.senai.hm.modelo.Transferencia;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;

public class DetalhePatrimonioController implements Initializable {

	@FXML
	private Label lbMotivo;
	@FXML
	private TextField buscarId, buscarDesc, buscarMod, buscarAmb;
	@FXML
	private TextArea txMotivo;
	@FXML
	private ImageView btnBuscarId, btnBuscarDesc, btnBuscarAmb, btnBuscarMod;

	@FXML
	private DatePicker buscarData;
	@FXML
	private RadioButton radioNovo, radioSeminovo, radioTodosSit, radioAtivo, radioInativo, radioTodosSta;
	@FXML
	private TableView<Patrimonio> tbPatrimonio;
	@FXML
	private TableColumn<Patrimonio, Long> columnId;
	@FXML
	private TableColumn<Patrimonio, String> columnDesc;
	@FXML
	private TableColumn<Patrimonio, String> columnModelo;
	@FXML
	private TableColumn<Patrimonio, String> columnCategoria;
	@FXML
	private TableColumn<Patrimonio, String> columnAmbiente;
	@FXML
	private TableColumn<Patrimonio, String> columnSituacao;
	@FXML
	private TableColumn<Patrimonio, String> columnData;
	@FXML
	private TableColumn<Patrimonio, String> columnFornecedor;
	@FXML
	private TableColumn<Patrimonio, Long> columnValor;
	@FXML
	private TableColumn<Patrimonio, String> columnDetalhe;
	@FXML
	Pane ap;
	@FXML
	private TableColumn<Patrimonio, String> columnStatus;
	private ObservableList<Patrimonio> listItens;
	private PatrimonioDAO daop;
	private TransferenciaDAO daot;
	private ToggleGroup grupo = new ToggleGroup();
	private ToggleGroup grupo2 = new ToggleGroup();
	private Patrimonio p;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daop = new PatrimonioDAO();
		daot = new TransferenciaDAO();
		popularTabelas();
		radioNovo.setToggleGroup(grupo);
		radioSeminovo.setToggleGroup(grupo);
		radioTodosSit.setToggleGroup(grupo);
		radioAtivo.setToggleGroup(grupo2);
		radioInativo.setToggleGroup(grupo2);
		radioTodosSta.setToggleGroup(grupo2);
	}

	public void ativar(ActionEvent event) {
		if (tbPatrimonio.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg6();
		} else if (p.isStatus() == true) {
			Mensagens.mostrarMsg7();
		} else {
			if (Mensagens.mostrarMsg12() == true) {
				System.out.println("alterando");
				daop.ativar(p);
				Mensagens.mostrarMsg1();
				popularTabelas();
			}
		}
	}

	public void inativar(ActionEvent event) {
		if (tbPatrimonio.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg6();
		} else if (p.isStatus() == false) {
			Mensagens.mostrarMsg7();
		} else if (p.isStatus()) {
			if (Mensagens.mostrarMsg11() == true) {
				try {
					Stage stage = new Stage();
					Parent root = FXMLLoader.load(getClass().getResource("/views/Realizar_Transferencia.fxml"));
					Scene scene = new Scene(root);
					stage.setScene(scene);
					stage.setTitle("Detalhe de Transferencia");
					stage.show();
					ap.getScene().getWindow().hide();
				} catch (IOException e) {
					Mensagens.mostrarMsg7();
				}
				popularTabelas();
			}
		}

	}

	public void popularTabelas() {
		listItens = FXCollections.observableArrayList(daop.buscarTodos());
		tbPatrimonio.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDesc.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getId_modelo().getDescricao());
					}
				});
		columnModelo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getId_modelo().getComponente());
					}
				});
		columnCategoria.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getId_modelo().getCategoria().getDescricao());
					}
				});
		columnAmbiente.setCellValueFactory(new PropertyValueFactory<>("id_ambiente"));
		columnSituacao.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
		columnData.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {

						return new SimpleStringProperty(param.getValue().getDataFormatada());
					}
				});
		columnFornecedor.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getId_modelo().getFornecedor().getNome());
					}
				});
		columnValor.setCellValueFactory(new PropertyValueFactory<>("Valor_unitario"));
		columnDetalhe.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getId_modelo().getDetalhe());
					}
				});
		columnStatus.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						if (param.getValue().isStatus()) {
							return new SimpleStringProperty("Ativo");
						} else {
							return new SimpleStringProperty("Inativo");
						}
					}
				});
		tbPatrimonio.setItems(listItens);
	}

	public void buscarId(ActionEvent e) {
		if (buscarId.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getId().equals(Long.parseLong(buscarId.getText().trim()))) {
					intensEncontrados.add(p);
				}
				if (!buscarId.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void btnBuscarId(MouseEvent e) {
		if (buscarId.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getId().equals(Long.parseLong(buscarId.getText().trim()))) {
					intensEncontrados.add(p);
				}
				if (!buscarId.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void buscarDesc(ActionEvent e) {
		if (buscarDesc.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getDescricao().toLowerCase().startsWith(buscarDesc.getText().toLowerCase())) {
					intensEncontrados.add(p);
				}
				if (!buscarDesc.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void btnBuscarDescricao(MouseEvent e) {
		if (buscarDesc.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getDescricao().toLowerCase().startsWith(buscarDesc.getText().toLowerCase())) {
					intensEncontrados.add(p);
				}
				if (!buscarDesc.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void buscarAmb(ActionEvent e) {
		if (buscarAmb.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getId_ambiente().getDescricao().toLowerCase().startsWith(buscarAmb.getText().toLowerCase())) {
					intensEncontrados.add(p);
				}
				if (!buscarAmb.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void btnBuscarAmb() {
		if (buscarAmb.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getId_ambiente().getDescricao().toLowerCase().startsWith(buscarAmb.getText().toLowerCase())) {
					intensEncontrados.add(p);
				}
				if (!buscarAmb.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void buscarMod(ActionEvent e) {
		if (buscarMod.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getId_modelo().getComponente().toLowerCase().startsWith(buscarMod.getText().toLowerCase())) {
					intensEncontrados.add(p);
				}
				if (!buscarMod.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void btnBuscarMod() {
		if (buscarMod.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			popularTabelas();
		} else {
			ObservableList<Patrimonio> intensEncontrados = FXCollections.observableArrayList();
			for (Patrimonio p : listItens) {
				if (p.getId_modelo().getComponente().toLowerCase().startsWith(buscarMod.getText().toLowerCase())) {
					intensEncontrados.add(p);
				}
				if (!buscarMod.equals("")) {
					tbPatrimonio.setItems(intensEncontrados);
				} else {
					popularTabelas();
				}
			}
		}
	}

	public void buscarNovo(ActionEvent event) {
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList();
		for (Patrimonio p : listItens) {
			if (p.getSituacao() == Situacao.NOVO) {
				itensEncontrados.add(p);
			}
		}
		if (radioNovo.isSelected()) {
			tbPatrimonio.setItems(itensEncontrados);
		} else {
			popularTabelas();
		}
	}

	public void buscarSeminovo(ActionEvent event) {
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList();
		for (Patrimonio p : listItens) {
			if (p.getSituacao() == Situacao.SEMINOVO) {
				itensEncontrados.add(p);
			}
		}
		if (radioSeminovo.isSelected()) {
			tbPatrimonio.setItems(itensEncontrados);
		} else {
			popularTabelas();
		}
	}

	public void buscarTodosSit(ActionEvent event) {
		popularTabelas();
	}

	public void buscarAtivo(ActionEvent event) {
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList();
		for (Patrimonio p : listItens) {
			if (p.isStatus()) {
				itensEncontrados.add(p);
			}
		}
		if (radioAtivo.isSelected()) {
			tbPatrimonio.setItems(itensEncontrados);
		} else {
			popularTabelas();
		}
	}

	public void buscarInativo(ActionEvent event) {
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList();
		for (Patrimonio p : listItens) {
			if (!p.isStatus()) {
				itensEncontrados.add(p);
			}
		}
		if (radioInativo.isSelected()) {
			tbPatrimonio.setItems(itensEncontrados);
		} else {
			popularTabelas();
		}
	}

	public void buscarTodosSta(ActionEvent event) {
		popularTabelas();
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

	public static LocalDate toLocalDate(Date d) {
		Instant instant = Instant.ofEpochMilli(d.getTime());
		LocalDate localDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}

	public String getDataFormatada(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(data);
	}

	public void buscarData(ActionEvent event) {
		ObservableList<Patrimonio> itensEncontrados = FXCollections.observableArrayList();
		String data = null;
		if (toDate(buscarData) != null) {
			data = getDataFormatada(toDate(buscarData));
			System.out.println("No Text: " + data);
			for (Patrimonio p : listItens) {
				System.out.println("No Banco: " + p.getDataFormatada());
				if (p.getDataFormatada().contains(data)) {
					itensEncontrados.add(p);
				}
			}
			if (!data.equals(null)) {
				tbPatrimonio.setItems(itensEncontrados);
			}
		} else {
			popularTabelas();
		}
	}

	public void selectItem(MouseEvent event) {
		if (tbPatrimonio.getFocusModel().getFocusedItem() != null) {
			p = tbPatrimonio.getFocusModel().getFocusedItem();
		}
		popularPatrimonio();
	}

	private void popularPatrimonio() {
		if (!p.isStatus()) {
			txMotivo.setEditable(false);
			txMotivo.setText(daot.buscarMotivo(p).getMotivo());
		} else {
			txMotivo.setEditable(false);
			txMotivo.setText("");
		}
	}

}
