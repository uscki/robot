package mennov1;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import bots.IBot;


public class EventBus {

	/**
	 * The Event Bus manages what happens
	 * 
	 * @author Benno Kruit
	 */
	private static EventBus master;
	private ConcurrentHashMap<Listener, Boolean> eventListeners;

	public static EventBus getInstance() {
		if (null == master) {
			master = new EventBus();
		}
		return master;
	}
	
	private EventBus() {
		eventListeners = new ConcurrentHashMap<Listener, Boolean>();
	}
	public void addListener(Listener l){
		if (!eventListeners.containsKey(l)) {
			eventListeners.put(l, false);
		}
	}
	public void removeListener(Listener l){
		if (eventListeners.containsKey(l)) {
			eventListeners.remove(l);
		}
	}
	public Boolean removeAllBots(String name){
		Boolean removed = false;
		for(Listener l : eventListeners.keySet()) {
			if (name.equals(l.getClass().getSimpleName())) {
				if (l instanceof IBot) {
					eventListeners.remove(l);
					removed = true;
				}
			}
		}
		return removed;
	}
	public int countBots() {
		int counter= 0;
		for(Listener l : eventListeners.keySet()) {
			if (l instanceof IBot) {
				counter++;
			}
		}
		return counter;
	}
	public String listBots() {
		String list = "";
		for(Listener l : eventListeners.keySet()) {
			if (l instanceof IBot) {
				list += l.getClass().getSimpleName() + ", ";
			}
		}
		return list;
	}
	
	public void event(EventObject e) {
		for(Listener l : eventListeners.keySet()) {
			if (l.wants(e)) {
				try {
					l.event(e);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}
}
