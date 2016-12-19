package br.senai.hm.mask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class CepMask implements ChangeListener<Boolean>, EventHandler<KeyEvent> {
	private TextField text;
	private String validador = "";

	public CepMask(TextField text) {
		this.text = text;
	}

	@Override
	public void handle(KeyEvent k) {

		String sub1, sub2, sub3, sub4, sub5, sub6, sub7, sub8, sub9, sub10, sub11, sub12, sub13, sub14, part1, part2,
				part3;
		String cep = text.getText();

		if (k.getCode().equals(k.getCode().BACK_SPACE) || k.getCode().equals(k.getCode().DELETE)) {
			text.setText("");
		}

		if (text.getText().length() == 8) {

			sub1 = cep.substring(0, 1);
			sub2 = cep.substring(1, 2);
			sub3 = cep.substring(2, 3);
			sub4 = cep.substring(3, 4);
			sub5 = cep.substring(4, 5);
			sub6 = cep.substring(5, 6);
			sub7 = cep.substring(6, 7);
			sub8 = cep.substring(7, 8);



			part1 = sub1 + sub2 + sub3 + ".";
			part2 = sub4 + sub5+ "-";
			part3 = sub6 + sub7 + sub8;

			cep = part1 + part2 + part3;

			text.setText(cep);

			validador = text.getText();

		} else if (text.getText().length() > 9 && text.getText().length() <= 9) {
			validador = text.getText();
		} else if (text.getText().length() >= 9) {
			text.setText(validador);
		}

	}

	@Override
	public void changed(ObservableValue<? extends Boolean> arg0, Boolean focusLost, Boolean arg2) {
		if (focusLost && text.getText().length() < 9) {
			text.setText("");

		}

	}

}
