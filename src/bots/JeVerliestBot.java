package bots;

import events.Event;
import events.JeVerliestEvent;
import events.Response;
import events.TextEvent;

public class JeVerliestBot implements IBot {
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
		else return new Response();
	}
}
