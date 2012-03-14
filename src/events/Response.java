package events;

import java.util.ArrayList;

public class Response { 
	private ArrayList<Event> events;
	public String response;

	
	public Response(String response) {
		this.response = response;
		events = new ArrayList<Event>();
	}
	public Response() {
		response = "";
		events = new ArrayList<Event>();
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public void addEvent(Event event)
	{
		events.add(event);	
	}

	public Event [] getEvents() {
		Event[] events2 = new Event[events.size()]; 
		
		events.toArray(events2);
		return events2;
	}
}
