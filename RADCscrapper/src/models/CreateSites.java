package models;

import java.util.ArrayList;

public class CreateSites {
	
	public SiteData MakeUCSite() {
		ArrayList<String>siteIdentifiers = new ArrayList<String>();
		siteIdentifiers.add("uc");
		siteIdentifiers.add("u of c");
		siteIdentifiers.add("university of chicago");
		
		SiteData ucData = new SiteData("University of Chicago", "5812 S Ellis Ave, Chicago, IL", 
				siteIdentifiers
				, 30);
		return ucData;
	}
	
	public SiteData MakeMGSite() {
		ArrayList<String>siteIdentifiers = new ArrayList<String>();
		siteIdentifiers.add("mg");
		siteIdentifiers.add("morton grove");
		siteIdentifiers.add("morton");
		
		SiteData mgData = new SiteData("Morton Grove", "9000 Waukegan Road, Morton Grove, IL", 
				siteIdentifiers
				, 15);
		return mgData;
	}
}
