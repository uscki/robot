package bots;

import events.Event;
import events.JeVerliestEvent;
import events.Response;
import events.TextEvent;
import events.TextEventInterface;

public class JeVerliestBot implements Bot,TextEventInterface {
	public Response handleEvents(Event event) {
		if(event instanceof TextEvent) {
			if(event.info.equals("loser")) {
				Response res = new Response();

				JeVerliestEvent jve = new JeVerliestEvent("Japie","Sjakie",10);
				res.setResponse("Haha, verloren!");
				res.addEvent(jve);

				return res;
			}
			return new Response();	
		}
		//should never happen
		else return null;
	}
}
