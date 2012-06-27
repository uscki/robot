package bots;

import mennov1.Listener;
import events.Event;

/**
 * 
 * @author Vincent Tunru
 * @category Framework
 * 
 * Bot interface, bots used by MennoV1 need to implement this.
 */
public interface IBot<SubEvent extends Event> extends Listener<SubEvent> {
	public void event(SubEvent e);
}
