package models;

import java.util.List;

public class SiteData {
	private String siteName;
	private String siteAddress;
	private List<String> siteIdentifiers;
	private int minutesAdjust;
	
	public SiteData(String siteName, String siteAddress, List<String> siteIdentifiers, int minutesAdjust) {
		super();
		this.siteName = siteName;
		this.siteAddress = siteAddress;
		this.siteIdentifiers = siteIdentifiers;
		this.minutesAdjust = minutesAdjust;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public List<String> getSiteIdentifiers() {
		return siteIdentifiers;
	}

	public void setSiteIdentifiers(List<String> siteIdentifiers) {
		this.siteIdentifiers = siteIdentifiers;
	}

	public int getMinutesAdjust() {
		return minutesAdjust;
	}

	public void setMinutesAdjust(int minutesAdjust) {
		this.minutesAdjust = minutesAdjust;
	}

	@Override
	public String toString() {
		return "SiteData [siteName=" + siteName + ", siteAddress=" + siteAddress + ", siteIdentifiers="
				+ siteIdentifiers + ", minutesAdjust=" + minutesAdjust + "]";
	}
	
	
}
