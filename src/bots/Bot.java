package bots;

import events.Event;
import events.Response;

/**
 * 
 * @author Vincent Tunru
 * @category Framework
 * 
 * Bot interface, bots used by MennoV1 need to implement this.
 */
public interface Bot {

	public Response handleEvents(Event event);
}
