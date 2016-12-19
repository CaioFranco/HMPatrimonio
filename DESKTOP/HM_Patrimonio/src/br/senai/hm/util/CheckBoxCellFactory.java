package br.senai.hm.util;

import com.mysql.jdbc.RowData;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.util.Callback;

public class CheckBoxCellFactory<S,T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

	@Override
	public TableCell<S, T> call(TableColumn<S, T> param) {
		return new CheckBoxTableCell<>();
	}
	
	private Pos alignment = Pos.CENTER;
	public Pos getAlignment() { return alignment; }
	public void setAlignment(Pos alignment) { this.alignment = alignment; }
	
	
	public class CheckboxCell extends TableCell<RowData, Boolean> {
		CheckBox checkbox;

		@Override
		protected void updateItem(Boolean arg0, boolean arg1) {
		    super.updateItem(arg0, arg1);
		        paintCell();
		}

		private void paintCell() {
		    if (checkbox == null) {
		        checkbox = new CheckBox();
		        checkbox.selectedProperty().addListener(new ChangeListener<Boolean>() {

		            public void changed(ObservableValue<? extends Boolean> ov,Boolean old_val, Boolean new_val) {
		                setItem(new_val);
		                
		               // ((RowData)getTableView().getItems().get(getTableRow().getIndex())).setSelected(new_val);
		            }
		        });
	
		    }
		    checkbox.setSelected(getValue());
		    setText(null);
		    setGraphic(checkbox);
		}

		private Boolean getValue() {
		    return getItem() == null ? false : getItem();
		}
	}
	

}
