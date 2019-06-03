package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ParseAppPage {
	
	private String projID;
	private String name;
	private String dob;
	private String dateTime;
	private String location;
	private String address;
	private String phone;
	private String apptName;
	
	public ParseAppPage(String projID, String name, String dob, String dateTime, String location, String address,
			String phone, String apptName) {
		super();
		this.projID = projID;
		this.name = name;
		this.dob = dob;
		this.dateTime = dateTime;
		this.location = location;
		this.address = address;
		this.phone = phone;
		this.apptName = apptName;
	}

	public String getProjID() {
		return projID;
	}

	public void setProjID(String projID) {
		this.projID = projID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		//need processing from app page 
		//Found cell 2 : Mrs. Jane Smith 
		//Baseline year: 2015 
	    //Education: 12		
		String[] lines = name.split("\\r?\\n");
        String splitName = lines[0];
		this.name = splitName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {		
		this.dateTime = dateTime;	
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getApptName() {
		return apptName;
	}

	public void setApptName(String apptName) {
		this.apptName = apptName;
	}

	public LocalDateTime convertDateTime (String date) {
		//String string = "January 2, 2010";
		String[] lines = dateTime.split("\\r?\\n");
        String[] splitDate = lines[0].split("—");
        String splitDateFirst = splitDate[0];
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE M/d/yyyy h:mm a ", Locale.ENGLISH);
		LocalDateTime convertedDate = LocalDateTime.parse(splitDateFirst, formatter);
		//System.out.println(covertedDate.); // 2010-01-02
		
		return convertedDate;
	}
	
	
	
	
	
	
}
