package models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class StaffTableData {
	private StringProperty staffNameProperty;
	private StringProperty staffNumberProperty;
	private StringProperty staffIdProperty;
	private StringProperty staffEmailProperty;

	public StaffTableData(String staffId, String staffName, String staffNumber, String staffEmail) {
	    this.staffNameProperty = new SimpleStringProperty(staffName);
	    this.staffNumberProperty = new SimpleStringProperty(staffNumber);
	    this.staffIdProperty = new SimpleStringProperty(staffId);  
	    this.staffEmailProperty = new SimpleStringProperty(staffEmail);  
	}
	
	public String getStaffNameProperty() {
		return this.staffNameProperty.get();
	}

	public void setStaffNameProperty(String staffName) {
		this.staffNameProperty.set(staffName);
	}

	public String getStaffNumberProperty() {
		return this.staffNumberProperty.get();
	}

	public void setStaffNumberProperty(String staffNumber) {
		this.staffNumberProperty.set(staffNumber);
	}

	public String getStaffIdProperty() {
		return this.staffIdProperty.get();
	}

	public void setStaffIdProperty(String staffId) {
		this.staffIdProperty.set(staffId);
	}

	public String getStaffEmailProperty() {
		return this.staffEmailProperty.get();
	}

	public void setStaffEmailProperty(String staffEmail) {
		this.staffEmailProperty.set(staffEmail);
	}
}