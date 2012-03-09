public class Response { 
	private ArrayList<Event> events;
	public String response;

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
		return events.ToArray();
	}
}
