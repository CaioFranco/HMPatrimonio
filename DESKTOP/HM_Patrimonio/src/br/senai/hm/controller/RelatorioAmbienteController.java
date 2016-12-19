package br.senai.hm.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import br.senai.hm.dao.AmbienteDAO;
import br.senai.hm.dao.PatrimonioDAO;
import br.senai.hm.modelo.Ambiente;
import br.senai.hm.modelo.Patrimonio;
import br.senai.hm.util.Mensagens;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;

public class RelatorioAmbienteController implements Initializable {

	private PatrimonioDAO daop;
	private AmbienteDAO daoa;
	private Ambiente a;

	@FXML
	private ComboBox<Ambiente> comboAmbiente;
	@FXML
	private AnchorPane ap;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		daoa = new AmbienteDAO();
		daop = new PatrimonioDAO();
		popularComboBox();
	}

	public void popularComboBox() {
		ObservableList<Ambiente> listAmbiente = FXCollections.observableArrayList();
		for (Ambiente a : daoa.buscarTodos()) {
			if (a.isStatus() != false) {
				listAmbiente.add(a);
			}
		}
		comboAmbiente.setItems(listAmbiente);
	}

	public void confirmar(ActionEvent event) {
		a = comboAmbiente.getValue();
		if (a == null) {
			Mensagens.mostrarMsg9();
		}else {
			System.out.println(a.getDescricao());
			List<Patrimonio> listaP = new ArrayList<Patrimonio>();
			for (Patrimonio p : daop.buscarTodos()) {
				if (p.getId_ambiente().getId().equals(a.getId())) {
					listaP.add(p);
				}
			}
			try {
				JasperReport pathjrxml = JasperCompileManager.compileReport(getClass().getResourceAsStream("/rels/relatorioAmbiente.jrxml"));
				JasperPrint jpPrint = JasperFillManager.fillReport(pathjrxml, null,
						new JRBeanCollectionDataSource(listaP, false));
				JasperViewer jv = new JasperViewer(jpPrint, false);
				jv.setVisible(true);
				jv.setTitle("Relatório");
			} catch (JRException e1) {
				// JOptionPane.showMessageDialog(null, "Erro" + e1);
				e1.printStackTrace();
			}
			ap.getScene().getWindow().hide();
		}

	}

	public void cancelar(ActionEvent event) {
		ap.getScene().getWindow().hide();
	}

}
