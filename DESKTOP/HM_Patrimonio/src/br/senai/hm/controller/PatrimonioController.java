package br.senai.hm.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.springframework.cglib.core.Local;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.ModeloDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Modelo;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.modelo.Permissao;
import br.senai.hm.modelo.Situacao;
import br.senai.hm.modelo.Usuario;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;


public class PatrimonioController implements Initializable {

	private Patrimonio p;
	private PatrimonioDAO daop;
	private ToggleGroup grupo = new ToggleGroup();
	private ModeloDAO daom;
	private AmbienteDAO daoa;
	private Stage stageModelo, stageAmbiente;
	private Image imgPadrao = new Image(getClass().getResourceAsStream("/resource/imagens/semimagem.jpg".toString()));

	@FXML
	private Pane ap;
	@FXML
	private TextField txIdentificador, txValorUnitario, txQtd, txDescricao, txCategoria, txFornecedor, buscarDesc,
			buscarAmb, buscarMod;
	@FXML
	private ComboBox<Modelo> comboModelo;
	@FXML
	private ComboBox<Ambiente> comboAmbiente;
	@FXML
	private ImageView imgFoto;

	@FXML
	private ImageView btnBuscarDesc, btnBuscarAmb, btnBuscarMod;

	@FXML
	private DatePicker dataEntrada;
	@FXML
	private RadioButton radioNovo, radioSeminovo;
	@FXML
	private TableView<Patrimonio> tbPatrimonio;
	@FXML
	private TableColumn<Patrimonio, Long> columnIdentificador;
	@FXML
	private TableColumn<Patrimonio, String> columnAmbiente;
	@FXML
	private TableColumn<Patrimonio, String> columnDescricao;
	@FXML
	private TableColumn<Patrimonio, String> columnModelo;
	@FXML
	private TableColumn<Patrimonio, String> columnDetalhe;
	@FXML
	private TableColumn<Patrimonio, String> columnSituacao;
	@FXML
	private TableColumn<Patrimonio, String> columnStatus;

	private ObservableList<Patrimonio> listItens;
	
	final Tooltip tooltip = new Tooltip();


	private LocalDate dtAtual = LocalDate.now();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daom = new ModeloDAO();
		daop = new PatrimonioDAO();
		daoa = new AmbienteDAO();
		radioNovo.setToggleGroup(grupo);
		radioNovo.setSelected(true);
		radioSeminovo.setToggleGroup(grupo);
		popularComboBox();

		popularTabelas();
		dataEntrada.setValue(LocalDate.now());

		Iterator<Patrimonio> i = listItens.iterator();
		Long pp = null;
		while (i.hasNext()) {
			pp = ((Long) i.next().getId());

		}
		// voltar aqui---------
		/*if (pp == null) {
			txIdentificador.setText("1");
		} else {
			System.out.println(pp);
			Long nv = pp + 1;
			String idt = nv.toString();
			txIdentificador.setText(idt);
		}*/

	}

	public void popularTabelas() {
		listItens = FXCollections.observableArrayList(daop.buscarTodos());
		tbPatrimonio.getItems().clear();
		columnIdentificador.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnAmbiente.setCellValueFactory(new PropertyValueFactory<>("id_ambiente"));
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
						// TODO Auto-generated method stub
						return new SimpleStringProperty(param.getValue().getId_modelo().getComponente());
					}
				});
		columnSituacao.setCellValueFactory(new PropertyValueFactory<>("Situacao"));
		columnDetalhe.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Patrimonio, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Patrimonio, String> param) {
						return new SimpleStringProperty(param.getValue().getId_modelo().getDetalhe());
					}
				});
		columnStatus.setCellValueFactory(
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
		tbPatrimonio.setItems(listItens);
	}

	public void popularComboBox() {
		ObservableList<Modelo> listModelo = FXCollections.observableArrayList();
		for (Modelo m : daom.buscarTodos()) {
			if (m.isStatus()) {
				listModelo.add(m);
			}
		}
		comboModelo.setItems(listModelo);
		ObservableList<Ambiente> listAmbiente = FXCollections.observableArrayList();
		for (Ambiente a : daoa.buscarTodos()) {
			if (a.isStatus()) {
				listAmbiente.add(a);
			}
		}
		comboAmbiente.setItems(listAmbiente);
	}

	public void autoPrechimentoModelo(ActionEvent event) {
		Modelo m = new Modelo();
		m = comboModelo.getValue();
		if (m != null) {
			txDescricao.setText(m.getComponente());
			txCategoria.setText(m.getCategoria().getDescricao());
			txFornecedor.setText(m.getFornecedor().getNome());
			try {
				if (m.getFoto64() != null) {
					imgFoto.setImage(stringToImage(m.getFoto64()));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public Image stringToImage(String imgBanco) throws IOException {
		byte[] montarImg = Base64.decode(imgBanco);
		BufferedImage bf = ImageIO.read(new ByteArrayInputStream(montarImg));
		Image imgem = SwingFXUtils.toFXImage(bf, null);
		return imgem;
	}




	public void salvar(ActionEvent event) {
		if (p == null) {
			p = new Patrimonio();
			p.setStatus(true);
		}

		if (validarCampos() == false) {
			Mensagens.mostrarMsg3();
		} else {
			Integer qtd = null;
			if (!txQtd.getText().isEmpty()) {
				qtd = Integer.parseInt(txQtd.getText());
				System.out.println("quantidade" + qtd);
			}
			p.setId(Long.parseLong(txIdentificador.getText().trim()));
			p.setValor_unitario(Double.parseDouble(txValorUnitario.getText().trim()));
			p.setId_modelo(comboModelo.getValue());
			p.setId_ambiente(comboAmbiente.getValue());

			if (radioNovo.isSelected()) {
				p.setSituacao(Situacao.NOVO);
				System.out.println("Novo");
			} else {
				p.setSituacao(Situacao.SEMINOVO);
				System.out.println("Seminovo");
			}
			p.setData_entrada(toDate(dataEntrada));
			if (dataEntrada.getValue().isAfter(dtAtual)) {
				Mensagens.mostrarMsg14a();
			}else if (daop.validarID(p) && qtd != 0) {
				if (qtd == 1) {
					daop.inserir(p);
				}else if (validarCadastroEmLote(p, qtd)) {
					cadastroEmLote(p, qtd);
				}else{
					Mensagens.mostrarMsg19();
				}
					limparTela();
			}else if (p.isStatus() && qtd != 0) {
				daop.alterar(p);
				Mensagens.mostrarMsg1();
				limparTela();
			}else if (qtd == 0) {
				Mensagens.mostrarMsg20();
				limparTela();
			}else {
				Mensagens.mostrarMsg7();
				limparTela();
			}
		}
		popularTabelas();
	}

	private boolean validarCadastroEmLote(Patrimonio p,Integer qtd){
		Long id = p.getId();
		int x = 0;
		while (x <= qtd) {
			for (Patrimonio patrimonio : listItens) {
				System.out.println("ID:"+id+"===PAT:"+patrimonio.getId());
				if (id.equals(patrimonio.getId())) {
					return false;
				}
			}
			id++;
			x++;
		}
		return true;
	}

	public void cadastroEmLote(Patrimonio pat, Integer qtd) {
		Patrimonio p ;
		Long id = pat.getId();
		for (int x = 0; x < qtd; x++) {
			p = new Patrimonio();
	     	p = pat;
			p.setId(id);
			daop.inserir(p);
			id++;
		}
		System.out.println("Inseriu Em Lote !!!!!!!");
		Mensagens.mostrarMsg1();
	}

	public void novo(ActionEvent event) {
		txQtd.setEditable(true);
		limparTela();
		dataEntrada.setValue(dtAtual);

	}

	public void limparTela() {
		txIdentificador.clear();
		txCategoria.clear();
		txDescricao.clear();
		txFornecedor.clear();
		txQtd.clear();
		txValorUnitario.clear();
		comboAmbiente.setValue(null);
		comboModelo.setValue(null);
		dataEntrada.setValue(null);
		imgFoto.setImage(imgPadrao);
		p = null;
	}

	public void selectItem(MouseEvent event) {
		if (tbPatrimonio.getFocusModel().getFocusedItem() != null) {
			p = tbPatrimonio.getFocusModel().getFocusedItem();
		}
		popularPatrimonio();
	}

	public void popularPatrimonio() {
		txIdentificador.setText(p.getId().toString());
		txQtd.setText("0");
		txQtd.setEditable(false);
		txValorUnitario.setText(p.getValor_unitario() + "");
		txDescricao.setText(p.getId_modelo().getComponente());
		txCategoria.setText(p.getId_modelo().getCategoria().getDescricao());
		txFornecedor.setText(p.getId_modelo().getFornecedor().getNome());
		comboModelo.setValue(p.getId_modelo());
		comboAmbiente.setValue(p.getId_ambiente());
		if (p.getSituacao() == Situacao.NOVO) {
			radioNovo.setSelected(true);
		} else {
			radioSeminovo.setSelected(true);
		}
		dataEntrada.setValue(toLocalDate(p.getData_entrada()));
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

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public void addModelo(ActionEvent event) {
		try {
			stageModelo = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/views/Modelo.fxml"));
			Scene scene = new Scene(root);
			stageModelo.setScene(scene);
			stageModelo.setResizable(false);
			stageModelo.setTitle("Cadastro de Modelo");
			stageModelo.setOnCloseRequest(new EventHandler<WindowEvent>() {
				public void handle(WindowEvent event) {
					//System.exit(0);
				}
			});
			stageModelo.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizarModelo(MouseEvent event) {
		popularComboBox();
	}

	public void addAmbiente(ActionEvent event) {
		try {
			stageAmbiente = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/views/Ambiente.fxml"));
			Scene scene = new Scene(root);
			stageAmbiente.setScene(scene);
			stageAmbiente.setResizable(false);
			stageAmbiente.setTitle("Cadastro de Ambiente");
			stageAmbiente.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					//System.exit(0);
				}
			});
			stageAmbiente.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void atualizarAmbiente(MouseEvent event) {
		popularComboBox();
	}

	public boolean validarCampos() {
		if (txValorUnitario.getText().trim().isEmpty() || txQtd.getText().trim().isEmpty() || comboModelo.getValue() == null
				|| comboAmbiente.getValue() == null || txIdentificador.getText().trim().isEmpty() || dataEntrada.getValue()==null) {
			return false;

		} else {
			return true;
		}
	}

	public void keyTyped(KeyEvent event) {
		if (!Character.isDigit(event.getCharacter().charAt(0))) {
			event.consume();
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
	


	public void KeyTyped(KeyEvent event) {
		int limitedType = 12;
		if (!Character.isDigit(event.getCharacter().charAt(0)) && event.getCharacter().charAt(0) != '.' ) {
			if (!txValorUnitario.getText().contains(".")  ) {
				event.consume();
			}
		} else if ((txValorUnitario.getText().length() >= limitedType)) {
			txValorUnitario.clear();
			tooltip.setText("Não pode ter mais do que 12 dígitos!");
			txValorUnitario.setTooltip(tooltip);
		}

	}
	
}
