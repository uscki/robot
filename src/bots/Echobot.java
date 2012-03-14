package bots;

import events.Event;
import events.Response;
import events.TextEvent;
import events.TextEventInterface;

/**
 * 
 * @author Vincent Tunru
 * @category Bots
 *
 * A very simple example bot who is basically a parrot.
 */
public class Echobot implements Bot, TextEventInterface {

	public Response handleEvents(Event event) {
		if(event instanceof TextEvent) {
			Response res = new Response();
			res.response = event.info;
			

			return res;
		}
		
		else 
			return new Response();
	}
}
