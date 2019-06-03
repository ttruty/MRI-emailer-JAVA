package models;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MakeHTML {
	
	public String makeFile(String firstDate, List<AppointmentData> data) throws IOException {
		String fileName = "next_weeks_orders" + firstDate + ".txt";
		List<String> lines = new ArrayList<String>();
				lines.add("<b>Orders for the week of: " + firstDate + "</b>" + "<br><br>");
				lines.add("<b>PLEASE NOTE: DRIVER SHOULD CALL PARTICIPANTS <i>15 MINUTES</i> BEFORE ARRIVAL.\r\n");
				lines.add("    DRIVER SHOULD CALL ON-SITE CONTACT FIVE MINUTES BEFORE ARRIVAL AND WAIT\r\n" );
				lines.add("    WITH PARTICIPANT UNTIL CONTACT MEETS THEM.</b><br><br>");
		for (AppointmentData singleAppointment : data) {
			if (singleAppointment != null)
			{
				System.out.println(singleAppointment.toString());
				lines.add(formAppointmentDetials(singleAppointment));
			}
		}
		Path file = Paths.get(fileName);
		Files.write(file, lines, Charset.forName("UTF-8"));
		//Files.write(file, lines, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
		return fileName;
	}
	
	private String formAppointmentDetials(AppointmentData data) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
		System.out.println(data.getSite().getMinutesAdjust());
		LocalDateTime adjustDropOff = data.getDate().minusMinutes(data.getSite().getMinutesAdjust());
		LocalDateTime adjustPickUpOff = data.getDate().plusHours(1);
		DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("EEE M/dd/yyyy", Locale.ENGLISH);
		String appointmentDate = data.getDate().format(dayFormatter);
		
		String dropOffTime = adjustDropOff.format(formatter);
		String pickUpTime = adjustPickUpOff.format(formatter);
		
		String string = 
		"Scan date: " + "<b>" + appointmentDate + "</b><br>\n" +
        "<b>" + data.getStaff().getFirstName() + " " + "(" + data.getStaff().getPhoneNumber() + ")" + " will be the contact at the site: </b>" +
        "<br>\n" +"<b>" + data.getSite().getSiteName() + "</b>" +
        "<br>\n" +
        "Drop off time: " +  dropOffTime +
        "<br>\n" +
        "Pick up time: " + pickUpTime +
        "<br>\n" + 
        "Participant name: " + data.getParticipant().getFullName() +
        "<br>\n" +
        "Participant address: " + data.getParticipant().getAddress() +
        "<br>\n" +
        "Participant Phone: " + data.getParticipant().getNumbers() +
        "<br>\n" +
        "MRI Site to drop off/pickup: " + "<b>" + data.getSite().getSiteName() + "</b>" + " " + 
        data.getSite().getSiteAddress() +
        "<br>\n" +
        "<br>\n" +
        "<br>\n";
		
		return string;	
	}
}
