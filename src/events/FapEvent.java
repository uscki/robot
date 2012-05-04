package events;

public class FapEvent extends Event {

	private String newFapSite;
	
	public FapEvent(String newFapSite)
	{
		this.newFapSite = newFapSite;
	}
	
	public String getNewFapSite()
	{
		return newFapSite;
	}
}
