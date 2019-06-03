package models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MakeStaffURLS {
	// print url
	// https://radc.rush.edu/radc/schedule/staffDailySchedule.htm?
	// month=05
	//&day=30
	//&year=2019
	//&intid=957
	//&view=print
	public Map<String,Staff> Generate(List<Staff> staffList, List<LocalDate> dates) {
		
		List<String> urlList = new ArrayList<String>();
		Map<String,Staff> map = new HashMap<String,Staff>();

		
		
		String baseURL = "https://radc.rush.edu/radc/schedule/staffDailySchedule.htm?";
		if (dates != null)
		  {
			  for (LocalDate date : dates) {
				  for (Staff staff : staffList) {
					  String addURL = String.format("month=%s&day=%s&year=%s&intid=%s", date.getMonthValue(), date.getDayOfMonth(), date.getYear(), staff.getID());
					  String newURL = baseURL + addURL;
					  urlList.add(newURL);
					  map.put(newURL, staff);
					  //System.out.println(newURL);
				  }
			  }
		  };	  
		  
		  return map;
		// /html/body/div[2]/table/tbody/tr[2]/td[3]/p[1]
		// /html/body/div[2]/table/tbody/tr[3]/td[3]/p[1]/text()[1]
	}
}
