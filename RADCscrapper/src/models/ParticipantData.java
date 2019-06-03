package models;

public class ParticipantData {
	
	private String projID;
	private String fullName;
	private String numbers;
	private String address;
	
	public ParticipantData(String projID, String fullName, String numbers,
			String address) {
		super();
		this.projID = projID;
		String[] lines = fullName.split("\\r?\\n");
        String splitName = lines[0];
		this.fullName = splitName;
		this.numbers = numbers;
		this.address = address;
	}

	public String getProjID() {
		return projID;
	}

	public void setProjID(String projID) {
		this.projID = projID;
	}

	public String getFullName() {
		
		return fullName;
	}

	public void setFullName(String fullName) {
		String[] lines = fullName.split("\\r?\\n");
        String splitName = lines[0];
		this.fullName = splitName;
	}

	public String getNumbers() {
		return numbers;
	}

	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "ParticipantData [projID=" + projID + ", fullName=" + fullName + ", numbers=" + numbers + ", address="
				+ address + "]";
	}
	
	

}
