package br.senai.hm.mask;

import java.security.KeyException;

import org.dom4j.Text;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CnpjMask implements ChangeListener<Boolean>, EventHandler<KeyEvent> {
	private TextField text;
	private String validador = "";

	public CnpjMask(TextField text) {
		this.text = text;
	}

	@Override
	public void handle(KeyEvent k) {

		String sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, sub11, sub12, sub13, sub14, part1, part2,
				part3, part4, part5;
		String cnpj = text.getText();

		if (k.getCode().equals(k.getCode().BACK_SPACE) || k.getCode().equals(k.getCode().DELETE)) {
			text.setText("");
		}

		if (text.getText().length() == 14) {

			sub1 = cnpj.substring(0, 1);
			sub2 = cnpj.substring(1, 2);
			sub3 = cnpj.substring(2, 3);
			sub4 = cnpj.substring(3, 4);
			sub5 = cnpj.substring(4, 5);
			sub6 = cnpj.substring(5, 6);
			sub7 = cnpj.substring(6, 7);
			sub8 = cnpj.substring(7, 8);
			sub9 = cnpj.substring(8, 9);
			sub10 = cnpj.substring(9, 10);
			sub11 = cnpj.substring(10, 11);
			sub12 = cnpj.substring(11, 12);
			sub13 = cnpj.substring(12, 13);
			sub14 = cnpj.substring(13, 14);

			part1 = sub1 + sub2 + ".";
			part2 = sub3 + sub4 + sub5 + ".";
			part3 = sub6 + sub7 + sub8 + "/";
			part4 = sub9 + sub10 + sub11 + sub12 + "-";
			part5 = sub13 + sub14;

			cnpj = part1 + part2 + part3 + part4 + part5;

			text.setText(cnpj);

			validador = text.getText();

		} else if (text.getText().length() > 14 && text.getText().length() <= 14) {
			validador = text.getText();
		} else if (text.getText().length() >= 18) {
			text.setText(validador);
		}

	}

	@Override
	public void changed(ObservableValue<? extends Boolean> arg0, Boolean focusLost, Boolean arg2) {
		if (focusLost && text.getText().length() < 18) {
			text.setText("");

		}

	}

}
