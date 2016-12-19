package br.senai.hm.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;

import org.hibernate.jpa.criteria.predicate.IsEmptyPredicate;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import application.Main;
import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.dao.TransferenciaDAO;
import br.senai.hm.modelo.Agendamento;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Conferencia;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Transferencia;
import br.senai.hm.util.Mensagens;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn.CellDataFeatures;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

public class RealizarTransferenciaController implements Initializable {

	static Stage stageAmbiente;
	private Image imgPadrao = new Image(getClass().getResourceAsStream("/resource/imagens/semimagem.jpg".toString()));
	private Transferencia t;
	private TransferenciaDAO daot;
	private AmbienteDAO daoa;
	private PatrimonioDAO daop;
	private Patrimonio p;
	private Ambiente a;

	@FXML
	private Label lbAmbiente;

	@FXML
	private Pane ap;

	@FXML
	private ComboBox<Ambiente> cmbOrigem;

	@FXML
	private TextField txfDestino;

	@FXML
	private DatePicker dtpDtTrasnferencia;

	@FXML
	private TextArea txaMotivo;

	@FXML
	private ImageView imgPatrimonio;

	@FXML
	private Button btnTransferir, btnSair, btnAmbienteOrigem;

	@FXML
	private TableView<Patrimonio> tbTransferencia;
	@FXML
	private TableColumn<Patrimonio, Long> columnId;
	@FXML
	private TableColumn<Patrimonio, String> columnDescricao;
	@FXML
	private TableColumn<Patrimonio, String> columnSituacao;
	@FXML
	private TableColumn<Patrimonio, String> columnModelo;
	@FXML
	private TableColumn<Patrimonio, String> columnEscolher;
	private ObservableList<Patrimonio> listItens;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		daot = new TransferenciaDAO();
		daoa = new AmbienteDAO();
		daop = new PatrimonioDAO();

		ObservableList<Ambiente> listAmbientes = FXCollections.observableArrayList();
		for (Ambiente a : daoa.buscarTodos()) {
			if (a.isStatus() != false) {
				listAmbientes.add(a);
			}
		}
		cmbOrigem.setItems(listAmbientes);
		dtpDtTrasnferencia.setValue(LocalDate.now());
	}

	public void selecionarAmbiente(ActionEvent event) {
		a = cmbOrigem.getValue();
		if (a != null) {
			lbAmbiente.setText(a.getDescricao());
			popularTabelas();
		}

	}

	public void popularTabelas() {
		listItens = FXCollections.observableArrayList(daop.buscarPorAmbiente(a));
		tbTransferencia.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDescricao.setCellValueFactory(
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
		columnSituacao.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
		columnEscolher.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						if (param.getValue().isStatus()) {
							return new SimpleStringProperty("Ativo");
						} else {
							return new SimpleStringProperty("Inativo");
						}
					}
				});
		tbTransferencia.setItems(listItens);
	}

	public void selectItem(MouseEvent event) {
		if (tbTransferencia.getFocusModel().getFocusedItem() != null) {
			p = tbTransferencia.getFocusModel().getFocusedItem();
		}
		popularPatrimonio();
	}

	private void popularPatrimonio() {
		try {
			imgPatrimonio.setImage(stringToImage(p.getId_modelo().getFoto64()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image stringToImage(String imgBanco) throws IOException {
		byte[] montarImg = Base64.decode(imgBanco);
		BufferedImage bf = ImageIO.read(new ByteArrayInputStream(montarImg));
		Image imgem = SwingFXUtils.toFXImage(bf, null);
		return imgem;
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

	public void transferir(ActionEvent event) {
		if (t == null) {
			t = new Transferencia();
		}

		if (cmbOrigem.getValue() == null || txfDestino.getText().isEmpty() || txaMotivo.getText().isEmpty()) {
			Mensagens.mostrarMsg9();
		} else if (tbTransferencia.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg10();

		} else if (validarCampos() == false) {
			Mensagens.mostrarMsg3();
		} else {

			t.setAmbiente(cmbOrigem.getValue());
			t.setData(toDate(dtpDtTrasnferencia));
			t.setDestino(txfDestino.getText().trim());
			t.setMotivo(txaMotivo.getText().trim());
			t.setPatrimonios(p);
			System.out.println(p.getId());
			daot.transferir(t, p);
			Mensagens.mostrarMsg1();
			limparTela();
		}

	}

	public void addAmbiete(ActionEvent event) {
		try {
			stageAmbiente = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/views/Ambiente.fxml"));
			Scene scene = new Scene(root);
			stageAmbiente.setTitle("Cadastro de Ambiente");
			stageAmbiente.setScene(scene);
			stageAmbiente.getIcons().add(new Image("resource/imagens/hm.png"));
			stageAmbiente.setOnCloseRequest(new EventHandler<WindowEvent>() {
				
				@Override
				public void handle(WindowEvent event) {
					System.exit(0);	
				}
			});
			stageAmbiente.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public static Stage getStageAmbiente() {
		return stageAmbiente;
	}

	public void limparTela() {
		txaMotivo.clear();
		txfDestino.clear();
		dtpDtTrasnferencia.setValue(null);
		t = null;
		p = null;
		imgPatrimonio.setImage(imgPadrao);
		popularTabelas();
	}

	public boolean validarCampos() {
		if (txaMotivo.getText().trim().isEmpty() || txfDestino.getText().trim().isEmpty()) {
			return false;

		} else {
			return true;
		}
	}

}
