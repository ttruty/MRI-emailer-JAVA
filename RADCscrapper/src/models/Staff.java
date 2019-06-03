package models;
public class Staff {
	
    private String id;
    private String firstName;
    private String phoneNumber;
    private String email;

    public Staff(String id, String firstName, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getID() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return "\n\nID: " + getID() + "\nName: " + getFirstName() + "\nPhone number: " + getPhoneNumber() + "\nEmail: " +
                getEmail();
    }
}