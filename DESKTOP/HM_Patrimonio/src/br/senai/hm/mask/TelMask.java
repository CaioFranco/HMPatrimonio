package br.senai.hm.mask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class TelMask implements ChangeListener<Boolean>, EventHandler<KeyEvent> {

	private TextField text;
	private String validador = "";

	public TelMask(TextField text) {
		this.text = text;
	}

	@Override
	public void handle(KeyEvent k) {

		String sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, par1, part2, part3;
		String tel = text.getText();

		if (k.getCode().equals(k.getCode().BACK_SPACE)
				|| k.getCode().equals(k.getCode().DELETE)) {
			text.setText("");
		}				
		if (text.getText().length() == 10) {

			sub1 = tel.substring(0, 1); // Instruções para quebrar
			sub2 = tel.substring(1, 2); // Cada caracter da String
			sub3 = tel.substring(2, 3); // e dividi-los individualmente.
			sub4 = tel.substring(3, 4);
			sub5 = tel.substring(4, 5);
			sub6 = tel.substring(5, 6);
			sub7 = tel.substring(6, 7);
			sub8 = tel.substring(7, 8);
			sub9 = tel.substring(8, 9);
			sub10 = tel.substring(9, 10);

			par1 = "(" + sub1 + sub2 + ")";
			part2 = sub3 + sub4 + sub5 + sub6 + "-";
			part3 = sub7 + sub8 + sub9 + sub10;

			tel = par1 + part2 + part3;									

			text.setText(tel);

			validador = text.getText();

		} else if (text.getText().length() > 10
				&& text.getText().length() <= 13) {
			validador = text.getText();
		} else if (text.getText().length() >= 13) {
			text.setText(validador);
		}

	}
	@Override
	public void changed(ObservableValue<? extends Boolean> arg0,
			Boolean focusLost, Boolean arg2) {
		if (focusLost && text.getText().length() < 13) {
			text.setText("");
		}

	}

}
