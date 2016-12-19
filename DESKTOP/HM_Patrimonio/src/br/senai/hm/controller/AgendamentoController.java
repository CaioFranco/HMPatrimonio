package br.senai.hm.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import application.Main;
import br.senai.hm.dao.AgendamentoDAO;
import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.ConferenciaDAO;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Dias;
import br.senai.hm.util.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Callback;

public class AgendamentoController implements Initializable {

	private static final Effect Glow = new Glow(1.0);
	private static final Effect Glow1 = new Glow(0.0);

	private Agendamento ag;
	private Ambiente a;
	private Conferencia c;
	private AmbienteDAO daoa;
	private AgendamentoDAO daoag;
	private ConferenciaDAO daoc;

	private LocalDate dtAtual = LocalDate.now();

	@FXML
	private Pane ap;

	@FXML
	private TextField txfAmbiente, txfBuscar;

	@FXML
	private DatePicker dtpAgendamento;

	@FXML
	private ComboBox<Dias> cmbDias;

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

	@FXML
	private Button btAgendar;

	@FXML
	private Button btSair;

	@FXML
	private ImageView btnBuscarDesc;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// a = new Ambiente();
		daoag = new AgendamentoDAO();
		daoa = new AmbienteDAO();
		daoc = new ConferenciaDAO();
		cmbDias.setItems(FXCollections.observableArrayList(Dias.values()));
		popularTabela();

	}

	private void popularTabela() {

		listItens = FXCollections.observableArrayList();
		for (Ambiente a : daoa.buscarTodos()) {
			if (a.isStatus() != false) {
				listItens.add(a);
			}
		}
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
			a = new Ambiente();
			a = tbAmbiente.getFocusModel().getFocusedItem();
			System.out.println(a.getDescricao());
			System.out.println(a.getId());

			popularAmbiente();
		}
	}

	public void popularAmbiente() {

		a.setId(a.getId());
		a.setDescricao(a.getDescricao());
		a.setStatus(true);
		a.setUsuario(a.getUsuario());

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

	public void mudarGlow(MouseEvent event) {
		btnBuscarDesc.setEffect(Glow);
	}

	public void normalizar(MouseEvent event) {
		btnBuscarDesc.setEffect(Glow1);
	}

	public static Date toDate(DatePicker datePicker) {
		if (datePicker.getValue() == null) {
			return null;
		}

		LocalDate ld = datePicker.getValue();
		Instant instant = ld.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		return date;
	}

	public void agendar(ActionEvent event) {
		System.out.println("PASSOU AQUI");
		ag = new Agendamento();

		if (dtpAgendamento.getValue() == null || cmbDias.getValue() == null
				|| tbAmbiente.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg9();
		} else if (dtpAgendamento.getValue().isBefore(dtAtual)) {
			Mensagens.mostrarMsg14();
		} else {

			ag.setAmbiente(a);
			ag.setData(toDate(dtpAgendamento));
			ag.setLimite(cmbDias.getValue());

			// daoag.agendar(ag);

			if (daoc.buscarPorAgendamento(a) != null) {
				Mensagens.mostrarMsg15();
			} else {
				daoag.agendar(ag);
				c = new Conferencia();
				c.setAgendamento(ag);
				daoc.inserir(c);
				limparTela();
				Mensagens.mostrarMsg1();
			}
		}

	}
	
	public void limparTela(){
		dtpAgendamento.setValue(null);
		cmbDias.setValue(null);
		txfAmbiente.clear();
		txfBuscar.clear();
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

}
