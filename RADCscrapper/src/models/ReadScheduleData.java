package models;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import controllers.LoginController;
import controllers.MainViewController;

public class ReadScheduleData {
	@SuppressWarnings("null")
	public List<AppointmentData> readData(Map<String,Staff> map) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		List<AppointmentData> data = new ArrayList<AppointmentData>();
		try (final WebClient webClient = new WebClient()) {
			for (Map.Entry<String,Staff> entry : map.entrySet())  {
					List<AppointmentData> dayAppsAppointments = new ArrayList<AppointmentData>();
					int dayCount = dayAppsAppointments.size();
					
					dayAppsAppointments = LoginController.webClient.scrapeAppPage(entry.getKey(), entry.getValue()) ;
					
					for (AppointmentData day : dayAppsAppointments) {
						data.add(day);
					}							
			}
		}
		return data;
	}
	
}
