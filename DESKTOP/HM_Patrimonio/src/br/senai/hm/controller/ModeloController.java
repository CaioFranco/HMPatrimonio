package br.senai.hm.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BASE64EncoderStream;

import application.Main;
import br.senai.hm.dao.CategoriaDAO;
import br.senai.hm.dao.FornecedorDAO;
import br.senai.hm.dao.ModeloDAO;
import br.senai.hm.modelo.Categoria;
import br.senai.hm.modelo.Fornecedor;
import br.senai.hm.modelo.Modelo;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import sun.misc.BASE64Encoder;

public class ModeloController implements Initializable {

	private static Stage stageCategoria, stageFornecedor;
	private Modelo m;
	private ModeloDAO daom;
	private CategoriaDAO daoc;
	private FornecedorDAO daof;
	private byte[] imgFoto;
	private Image imgPadrao = new Image(getClass().getResourceAsStream("/resource/imagens/semimagem.jpg".toString()));

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daom = new ModeloDAO();
		daof = new FornecedorDAO();
		daoc = new CategoriaDAO();
		populandoComboBox();
		populandoTabelas();
	}

	@FXML
	private AnchorPane ap;
	@FXML
	private TextField txId, txBuscarDesc, txBuscarComp, txDescricao, txComponente, txDepreciacao, txMarca;
	@FXML
	private TextArea txDetalhe;
	@FXML
	private ComboBox<Categoria> comboCategoria;
	@FXML
	private ComboBox<Fornecedor> comboFornecedor;
	@FXML
	private Button btnCategoria, btnFornecedor, btnSalvar, btnSair, btnNovo;
	@FXML
	private ImageView imgModelo = new ImageView();
	@FXML
	private ImageView btnBuscarDesc, btnBuscarComp;
	@FXML
	private TableView<Modelo> tbModelo;
	@FXML
	private TableColumn<Modelo, Long> columnId;
	@FXML
	private TableColumn<Modelo, String> columnDesc;
	@FXML
	private TableColumn<Modelo, String> columnMarca;
	@FXML
	private TableColumn<Modelo, String> columnComp;
	@FXML
	private TableColumn<Modelo, Fornecedor> columnForn;
	@FXML
	private TableColumn<Modelo, String> columnStatus;

	private static ObservableList<Modelo> listItens;

	public void populandoComboBox() {
		ObservableList<Fornecedor> listFornecedores = FXCollections.observableArrayList(daof.buscarTodos());
		comboFornecedor.setItems(listFornecedores);
		ObservableList<Categoria> listCategorias = FXCollections.observableArrayList(daoc.buscarTodos());
		comboCategoria.setItems(listCategorias);
	}

	public void populandoTabelas() {
		listItens = FXCollections.observableArrayList(daom.buscarTodos());
		tbModelo.getItems().clear();
		columnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		columnDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
		columnMarca.setCellValueFactory(new PropertyValueFactory<>("Marca"));
		columnComp.setCellValueFactory(new PropertyValueFactory<>("Componente"));
		columnForn.setCellValueFactory(new PropertyValueFactory<>("Fornecedor"));
		columnStatus.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Modelo, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<Modelo, String> param) {
						if (param.getValue().isStatus()) {
							return new SimpleStringProperty("Ativo");
						} else {
							return new SimpleStringProperty("Inativo");
						}
					}
				});
		tbModelo.setItems(listItens);
	}

	public void addCategoria(ActionEvent event) {
		try {
			stageCategoria = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/views/Categoria.fxml"));
			Scene scene = new Scene(root);
			stageCategoria.setScene(scene);
			stageCategoria.setResizable(false);
			stageCategoria.setTitle("Cadastro de Categoria");
			stageCategoria.getIcons().add(new Image("resource/imagens/hm.png"));
	
			stageCategoria.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void addFornecedor(ActionEvent event) {
		try {
			stageFornecedor = new Stage();
			Parent root = FXMLLoader.load(getClass().getResource("/views/Fornecedor.fxml"));
			Scene scene = new Scene(root);
			stageFornecedor.setScene(scene);
			stageFornecedor.setResizable(false);
			stageFornecedor.setTitle("Cadastro de Fornecedor");
			stageFornecedor.getIcons().add(new Image("resource/imagens/hm.png"));
			stageFornecedor.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void autoPrenchimento(ActionEvent event) {
		Categoria c = new Categoria();
		c = comboCategoria.getValue();
		if (c != null) {
			txDepreciacao.setText(c.getDepreciacao() + "");
		}
	}

	public void atualizarCategoria(MouseEvent event) {
		populandoComboBox();
	}

	public void atualizarFornecedor(MouseEvent event) {
		populandoComboBox();
	}

	public void salvar(ActionEvent event) {
		if (m == null) {
			m = new Modelo();
			m.setStatus(true);
		}

		if (validarCampos() == false) {
			Mensagens.mostrarMsg3();
		} else {
			m.setDescricao(txDescricao.getText().trim());
			m.setComponente(txComponente.getText().trim());
			m.setDetalhe(txDetalhe.getText().trim());
			m.setMarca(txMarca.getText().trim());
			m.setFornecedor(comboFornecedor.getValue());
			m.setCategoria(comboCategoria.getValue());
			m.setFoto(imgFoto);
			if (m.getId() == null) {
				daom.inserir(m);
				Mensagens.mostrarMsg1();
				limparTela();
			} else if (m.isStatus()) {
				daom.alterar(m);
				Mensagens.mostrarMsg1();
			}else {
				Mensagens.mostrarMsg7();
			}
			populandoTabelas();
		}

	}

	public void escolherImagem(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilters = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg",
				"*.gif");
		fileChooser.getExtensionFilters().add(extFilters);
		File file = fileChooser.showOpenDialog(Main.getStage());
		System.out.println(file);
		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			System.out.println(bufferedImage);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			imgModelo.setImage(image);
			imgFoto = imageToByte(file.toString());

		} catch (IOException e) {
			System.out.println(e);
		}
	}

	public byte[] imageToByte(String image) throws IOException {
		InputStream is = null;
		byte[] buffer = null;
		is = new FileInputStream(image);
		buffer = new byte[is.available()];
		is.read(buffer);
		is.close();
		return buffer;
	}

	public Image stringToImage(String imgBanco) throws IOException {
		byte[] montarImg = Base64.decode(imgBanco);
		BufferedImage bf = ImageIO.read(new ByteArrayInputStream(montarImg));
		Image imgem = SwingFXUtils.toFXImage(bf, null);
		return imgem;
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public void novo(ActionEvent event) {
		limparTela();
	}

	public void inativar(ActionEvent event) {
		if (tbModelo.getSelectionModel().isEmpty()) {
			Mensagens.mostrarMsg6();
		} else if (m.isStatus() == false) {
			Mensagens.mostrarMsg7();
		} else if (Mensagens.mostrarMsg5() == true) {
			daom.inativar(m);
			populandoTabelas();
		}

	}

	public void selectItem(MouseEvent event) {
		if (tbModelo.getFocusModel().getFocusedItem() != null) {
			m = tbModelo.getFocusModel().getFocusedItem();
		}
		popularModelo();
	}

	public void popularModelo() {
		txId.setText(m.getId().toString());
		txDescricao.setText(m.getDescricao());
		txComponente.setText(m.getComponente());
		txDetalhe.setText(m.getDetalhe());
		txMarca.setText(m.getMarca());
		comboCategoria.setValue(m.getCategoria());
		comboFornecedor.setValue(m.getFornecedor());
		if (m.getFoto64() == null) {
			imgModelo.setImage(imgPadrao);
		} else {
			try {
				imgModelo.setImage(stringToImage(m.getFoto64()));
				imgFoto = Base64.decode(m.getFoto64());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void limparTela() {
		txDescricao.clear();
		txComponente.clear();
		txDetalhe.clear();
		txMarca.clear();
		comboCategoria.setValue(null);
		comboFornecedor.setValue(null);
		imgModelo.setImage(imgPadrao);
		m = null;
	}

	public void buscarDesc(ActionEvent event) {
		if (txBuscarDesc.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			populandoTabelas();
		} else {
			ObservableList<Modelo> itensEncontrados = FXCollections.observableArrayList();
			for (Modelo m : listItens) {
				if (m.getDescricao().toLowerCase().startsWith(txBuscarDesc.getText().toLowerCase())) {
					itensEncontrados.add(m);
				}
			}
			if (!txBuscarDesc.equals("")) {
				tbModelo.setItems(itensEncontrados);
			} else {
				populandoTabelas();
			}
		}

	}

	public void btnBuscarDesc(MouseEvent event) {
		if (txBuscarDesc.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			populandoTabelas();
		} else {
			ObservableList<Modelo> itensEncontrados = FXCollections.observableArrayList();
			for (Modelo m : listItens) {
				if (m.getDescricao().toLowerCase().startsWith(txBuscarDesc.getText().toLowerCase())) {
					itensEncontrados.add(m);
				}
			}
			if (!txBuscarDesc.equals("")) {
				tbModelo.setItems(itensEncontrados);
			} else {
				populandoTabelas();
			}
		}
	}

	public void buscarComp(ActionEvent event) {
		if (txBuscarComp.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			populandoTabelas();
		} else {
			ObservableList<Modelo> itensEncontrados = FXCollections.observableArrayList();
			for (Modelo m : listItens) {
				if (m.getComponente().toLowerCase().startsWith(txBuscarComp.getText().toLowerCase())) {
					itensEncontrados.add(m);
				}
			}
			if (!txComponente.equals("")) {
				tbModelo.setItems(itensEncontrados);
			} else {
				populandoTabelas();
			}
		}

	}

	public void btnbuscarComp(MouseEvent event) {
		if (txBuscarComp.getText().trim().isEmpty()) {
			Mensagens.mostrarMsg4();
			populandoTabelas();
		} else {
			ObservableList<Modelo> itensEncontrados = FXCollections.observableArrayList();
			for (Modelo m : listItens) {
				if (m.getComponente().toLowerCase().startsWith(txBuscarComp.getText().toLowerCase())) {
					itensEncontrados.add(m);
				}
			}
			if (!txComponente.equals("")) {
				tbModelo.setItems(itensEncontrados);
			} else {
				populandoTabelas();
			}
		}
	}

	public boolean validarCampos() {
		if (txDescricao.getText().trim().isEmpty() || txComponente.getText().trim().isEmpty() || txMarca.getText().trim().isEmpty()
				|| comboCategoria.getValue() == null || comboFornecedor.getValue() == null) {
			return false;

		} else {
			return true;
		}
	}
}
