package models;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class HTMLUnit {
	private boolean Authenticated = false;
	private static final WebClient webClient = new WebClient(BrowserVersion.CHROME);
	// start of static block  
    static { 
    	//turn warning off for htmlunit
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		} // end of static block  
	
    public void loginForm(String username, String password) throws Exception {
	    
		
		try {			
	        // Get the first page
	        final HtmlPage page1 = webClient.getPage("https://radc.rush.edu/radc/login/login.jsp");
	
	        // Get the form that we are dealing with and within that form, 
	        // find the submit button and the field that we want to change.
	        final HtmlForm form = page1.getForms().get(0);
	
	        //final HtmlSubmitInput button = form.getInputByName("submit");
	        final HtmlTextInput userTextField = form.getInputByName("username");
	        final HtmlInput pwTextField = form.getInputByName("password");
	
	        // Change the value of the text field
	        userTextField.type(username);
	        pwTextField.type(password);
	        
	     // create a submit button - it doesn't work with 'input'
	        HtmlElement button = (HtmlElement) page1.createElement("button");
	        button.setAttribute("type", "submit");

	        // append the button to the form
	        form.appendChild(button);

	        // submit the form
	        //page = button.click();
	
	        // Now submit the form by clicking the button and get back the second page.
	        final HtmlPage page2 = button.click();
	        final String pageAsText = page2.asText();
	        
	        if (pageAsText.contains("RADC Login"))
	        {
	        	setAuthenticated(false);
	        	System.out.println("Not Logged In");
	        }
	        else {
	        	setAuthenticated(true);
	        	System.out.println("Logged In");
	        }
	    } 
		catch (FailingHttpStatusCodeException | IOException e) 
		{
            e.printStackTrace();
	    }
	}
	
	public boolean isAuthenticated() {
		return Authenticated;
	}

	public void setAuthenticated(boolean authenticated) {
		Authenticated = authenticated;
	}
	
	public List<AppointmentData> scrapeAppPage(String url, Staff staff)
	{
		List<AppointmentData> appData = new ArrayList<AppointmentData>();			
		try {			
	        // Get the first page
	        final HtmlPage page = webClient.getPage(url);
			//get list of all divs
	        System.out.println(url);
	        
	        String targetAppt =  "Neuro-Imaging - MRI Scan";
	        appData = parsePage(page, targetAppt, staff);
	    }
		catch (FailingHttpStatusCodeException | IOException e) 
		{
            e.printStackTrace();
	    }
		return appData;
		// body > div.container > table
	}
	
	public String scrapeParticipantPage(String url)
	{
		String partAddress = null;
		try {			
	        // Get the first page
	        final HtmlPage page = webClient.getPage(url);
			//get list of all divs
	        System.out.println(url);
	        //
	        partAddress = parseParticipantAddress(page);
		}
		catch (FailingHttpStatusCodeException | IOException e) 
		{
            e.printStackTrace();
	    }
		// body > div.container > table
		return partAddress;
	}
	
	private List<AppointmentData> parsePage(HtmlPage page, String ApptType, Staff staff){
		//AppointmentData singleAppt = new AppointmentData();
		List<AppointmentData> appInfo = new ArrayList<AppointmentData>();
		String projID = null;
		String name= null;
		String dob= null;
		String dateTime= null;
		String location= null;
		String address= null;
		String phone= null;
		String apptName= null;
		
		final List<?> divs =  page.getByXPath("/html/body/div[2]/table/tbody/tr");
		System.out.println(divs.size());
		int rowCount = divs.size() - 1;
		
		for (Object div : divs) {
			ParticipantData partData = null;
        	final HtmlTableRow row = (HtmlTableRow) div;
        	
        	int cellCount = 0;        	
        	if (row.asText().contains(ApptType))
        	{
        		for (final HtmlTableCell cell : row.getCells()) {
                    System.out.println("   Found cell " + cellCount +" : " + cell.asText());
                    switch (cellCount) {
                    case 1:
                      projID = cell.asText();
                      break;
                    case 2:
                      name= cell.asText();
                      break;
                    case 3:
                      dob= cell.asText();
                      break;
                    case 4:
                      dateTime = cell.asText();
                      break;
                    case 5:       
                    	location = cell.asText().toLowerCase();
                    	System.out.println("Site=(" + location.length() + ")");
                    	if (location.length() == 7)
                    	{
                    		location = "no site";
                    	}
                    	break;
                    case 6:
                      address = cell.asText();
                      break;
                    case 7:
                      phone = cell.asText();
                      break;
                    case 8:
                      apptName = cell.asText();
                      break;
                  }                    
                    cellCount++;
            	}
        		ParseAppPage parseApp = new ParseAppPage(projID, name, dob, dateTime, location,address, phone, apptName);
                LocalDateTime convertedDate = parseApp.convertDateTime(dateTime);
                String partURL = "https://radc.rush.edu/radc/participant/summary.htm?projid=";
            	partURL = partURL + projID;
            	String partAddress = scrapeParticipantPage(partURL);     
            	String[] nameSplit = name.split(" ");
            	
            	CreateSites makeSites = new CreateSites();
            	SiteData mg = makeSites.MakeMGSite();
            	SiteData uc = makeSites.MakeUCSite();
            	SiteData ns = makeSites.MakeNoSite();
            	SiteData locationSelection = null;
            	if (mg.getSiteIdentifiers().parallelStream().anyMatch(location::contains)) {locationSelection = mg;}
            	if (uc.getSiteIdentifiers().parallelStream().anyMatch(location::contains)) {locationSelection = uc;}
            	if (ns.getSiteIdentifiers().parallelStream().anyMatch(location::contains)) {locationSelection = ns;}
            	ParticipantData partInfo = new ParticipantData(projID, name, phone, partAddress);
            	AppointmentData singleApp = new AppointmentData(convertedDate, staff, partInfo, locationSelection, false, apptName);
            	//System.out.println(appInfo.toString());
            	appInfo.add(singleApp);
        	}
        }
        System.out.println();
                
		return appInfo;
		}
	
	private String parseParticipantAddress(HtmlPage page){
		//AppointmentData singleAppt = new AppointmentData();
		String address = null;
		// //*[@id="overview"]/div/div[1]/div[1]/div[2]/table[1]/tbody/tr[3]/td
		
		final List<?> divs =  page.getByXPath("//*[@id=\"overview\"]/div/div[1]/div[1]/div[2]/table[1]/tbody/tr[3]/td");
		for (Object div : divs) {
			HtmlTableCell cell = (HtmlTableCell) div;
        	address = cell.asText();
		//System.out.println();
        
		//ParticipantData participantList = new ParticipantData();        
		
		}
		return address;
	}
}

