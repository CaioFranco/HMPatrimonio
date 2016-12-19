package br.senai.hm.controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.transaction.Transactional;

import org.controlsfx.dialog.Dialogs;
import org.springframework.stereotype.Controller;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import application.Main;
import br.senai.hm.dao.EmpresaDAO;
import br.senai.hm.dao.UsuarioDAO;
import br.senai.hm.mask.CepMask;
import br.senai.hm.mask.CnpjMask;
import br.senai.hm.mask.ControllerCepMask;
import br.senai.hm.mask.ControllerCnpJMask;
import br.senai.hm.mask.ControllerTelMask;
import br.senai.hm.mask.TelMask;
import br.senai.hm.modelo.Empresa;
import br.senai.hm.modelo.Estados;
import br.senai.hm.util.Mensagens;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@Transactional
@Controller
public class EmpresaController implements Initializable {

	private Image padrao = new Image(getClass().getResource("/resource/imagens/semimagem.jpg").toString());
	private Empresa emp;
	private EmpresaDAO daoe;
	private UsuarioDAO daou;
	private byte[] logo;
	private static Stage stage;

	@FXML
	private Pane ap;
	@FXML
	private Button btnNovo;

	@FXML
	private Button btnSalvar;

	@FXML
	private Button btnSair;

	@FXML
	private Button btnEscolher;

	@FXML
	private ImageView imgLogo = new ImageView();;

	@FXML
	private TextField txfCnpj;
	@FXML
	private TextField txfRazao;
	@FXML
	private TextField txfNome;
	@FXML
	private TextField txfEndereco;
	@FXML
	private TextField txfCidade;
	@FXML
	private TextField txfCep;
	@FXML
	private TextField txfPais;
	@FXML
	private TextField txfTelefone;
	@FXML
	private TextField txfEmail;

	@FXML
	private ComboBox<Estados> cmbEstado = new ComboBox<>(FXCollections.observableArrayList(Estados.values()));

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private Pattern pattern;
	private Matcher matcher;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoe = new EmpresaDAO();
		daou = new UsuarioDAO();
		cmbEstado.setItems(FXCollections.observableArrayList(Estados.values()));
		exibir(1L);

		txfCnpj.focusedProperty().addListener(new CnpjMask(txfCnpj));
		txfCnpj.setOnKeyReleased(new CnpjMask(txfCnpj));
		txfCnpj.addEventFilter(KeyEvent.KEY_TYPED, new ControllerCnpJMask());

		txfCep.focusedProperty().addListener(new CepMask(txfCep));
		txfCep.setOnKeyReleased(new CepMask(txfCep));
		txfCep.addEventFilter(KeyEvent.KEY_TYPED, new ControllerCepMask());

		txfTelefone.focusedProperty().addListener(new TelMask(txfTelefone));
		txfTelefone.setOnKeyReleased(new TelMask(txfTelefone));
		txfTelefone.addEventFilter(KeyEvent.KEY_TYPED, new ControllerTelMask());

		pattern = Pattern.compile(EMAIL_PATTERN);

		txfPais.setText("Brasil");
		txfPais.setEditable(false);
	}

	public EmpresaController() {
		stage = Main.getStage();
	}

	public void sair(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

	public void escolherImagem(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg",
				"*.gif");
		fileChooser.getExtensionFilters().add(extFilter);
		File file = fileChooser.showOpenDialog(Main.getStage());
		System.out.println(file);

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			imgLogo.setImage(image);
			logo = imageToByte(file.toString());
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
		Image imagem = SwingFXUtils.toFXImage(bf, null);

		return imagem;

	}

	public void salvar(ActionEvent event) throws IOException {

		if (emp == null) {
			emp = new Empresa();
		}

		if (validarCampos() == false) {
			Mensagens.mostrarMsg3();

		} else if (validarEmail(txfEmail.getText().trim()) == false) {
			Mensagens.mostrarMsg2();
		} else {
			emp.setId(1L);
			emp.setCnpj(txfCnpj.getText().trim());
			emp.setRazao_social(txfRazao.getText().trim());
			emp.setNome_fantasia(txfNome.getText().trim());
			emp.setEndereco(txfEndereco.getText().trim());
			emp.setCep(txfCep.getText().trim());
			emp.setCidade(txfCidade.getText().trim());
			emp.setPais(txfPais.getText().trim());
			emp.setEstado(cmbEstado.getValue());
			emp.setTelefone(txfTelefone.getText().trim());
			emp.setEmail(txfEmail.getText().trim());
			emp.setLogo(logo);

			System.out.println("passou aqui");
			if (emp.getId() == null) {
				daoe.inserir(emp);
				System.out.println("1 " + emp.getId());
				System.out.println("salvou");
				Mensagens.mostrarMsg1();


			} else {
				System.out.println("2 " + emp.getId());
				daoe.alterar(emp);
				System.out.println("alterou");
				Mensagens.mostrarMsg1();
				sair(event);
				if (daou.buscarAdministrador() == null) {
					Parent root = FXMLLoader.load(getClass().getResource("/views/Usuario.fxml"));
					String titulo = "Cadastar Usuário";
					abrirPainel(root, titulo, stage);
				}
			}

		}

	}

	public void abrirPainel(Parent root, String titulo, Stage stage) {
		try {
			stage = new Stage();
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.setTitle(titulo);
			stage.setResizable(false);
			Main.stage = stage;
			stage.getIcons().add(new Image("resource/imagens/hm.png"));

			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exibir(Long id) {
		if (daoe.buscarId() != null) {
			emp = new EmpresaDAO().buscarId();
			txfCnpj.setText(emp.getCnpj());
			txfRazao.setText(emp.getRazao_social());
			txfNome.setText(emp.getNome_fantasia());
			txfEndereco.setText(emp.getEndereco());
			txfCidade.setText(emp.getCidade());
			cmbEstado.setValue(emp.getEstado());
			txfCep.setText(emp.getCep());
			txfPais.setText(emp.getPais());
			txfTelefone.setText(emp.getTelefone());
			txfEmail.setText(emp.getEmail());

			if (emp.getLogo64() == null) {
				imgLogo.setImage(padrao);
			} else {
				try {
					imgLogo.setImage(stringToImage(emp.getLogo64()));
					logo = Base64.decode(emp.getLogo64());
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

	public boolean validarEmail(String hex) {
		matcher = pattern.matcher(hex);
		return matcher.matches();
	}

	public boolean validarCampos() {
		if (txfCnpj.getText().trim().isEmpty() || txfRazao.getText().trim().isEmpty() || txfNome.getText().trim().isEmpty()
				|| txfEndereco.getText().trim().isEmpty() || txfCidade.getText().trim().isEmpty() || txfCep.getText().trim().isEmpty()
				|| txfPais.getText().trim().isEmpty() || txfTelefone.getText().trim().isEmpty() || cmbEstado.getValue() == null) {
			return false;

		} else {
			return true;
		}

	}

	public static Stage getStage() {
		return stage;
	}

}
