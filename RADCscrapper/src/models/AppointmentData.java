package models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class AppointmentData {
	private LocalDateTime date;
	private Staff staff;
	private ParticipantData participant;
	private SiteData site;
	private boolean couple;
	private String assessment;

	public AppointmentData(LocalDateTime date, Staff staff, ParticipantData participant, SiteData site, boolean couple,
			String assessment) {
		super();
		this.date = date;
		this.staff = staff;
		this.participant = participant;
		this.site = site;
		this.couple = couple;
		this.assessment = assessment;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Staff getStaff() {
		return staff;
	}

	public void setStaff(Staff staff) {
		this.staff = staff;
	}

	public ParticipantData getParticipant() {
		return participant;
	}

	public void setParticipant(ParticipantData participant) {
		this.participant = participant;
	}

	public SiteData getSite() {
		return site;
	}

	public void setSite(SiteData site) {
		this.site = site;
	}

	public boolean isCouple() {
		return couple;
	}

	public void setCouple(boolean couple) {
		this.couple = couple;
	}

	public String getAssessment() {
		return assessment;
	}

	public void setAssessment(String assessment) {
		this.assessment = assessment;
	}
	
	
	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE M/dd/yyyy h:mm a", Locale.ENGLISH);
		String text = date.format(formatter);
		System.out.println(date); // 2010-01-02
		return "AppointmentData [date=" + text + ", staff=" + staff + ", participant=" + participant.toString() + ", site=" + site
				+ ", couple=" + couple + ", assessment=" + assessment + "]";
	}
	
	
}
