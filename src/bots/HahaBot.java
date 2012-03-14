package bots;

import events.Event;
import events.JeVerliestEvent;
import events.JeVerliestEventInterface;
import events.Response;

public class HahaBot implements Bot,JeVerliestEventInterface{
	public Response handleEvents(Event event) {
		if(event instanceof JeVerliestEvent) {
			return new Response("Je zuigt, " + ((JeVerliestEvent) event).verliezer + "!");	
		}

		//should never happen
		else return null;
	}
}
