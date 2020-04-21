package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Line {

	private StringProperty col1 = new SimpleStringProperty();
	private StringProperty col2 = new SimpleStringProperty();
	private StringProperty col3 = new SimpleStringProperty();
	
	public Line (String col1, String col2, String col3) {
		setCol1(col1);
		setCol2(col2);
		setCol3(col3);
	}
	
	public final StringProperty col1Property() {
		return this.col1;
	}
	
	public final String getCol1() {
		return this.col1Property().get();
	}
	
	public final void setCol1(final String col1) {
		this.col1Property().set(col1);
	}
	
	public final StringProperty col2Property() {
		return this.col2;
	}
	
	public final String getCol2() {
		return this.col2Property().get();
	}
	
	public final void setCol2(final String col2) {
		this.col2Property().set(col2);
	}
	
	public final StringProperty col3Property() {
		return this.col3;
	}
	
	public final String getCol3() {
		return this.col3Property().get();
	}
	
	public final void setCol3(final String col3) {
		this.col3Property().set(col3);
	}
	
	
}
