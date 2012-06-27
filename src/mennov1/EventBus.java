package mennov1;
import java.util.EventObject;
import java.util.HashSet;


public class EventBus {

	/**
	 * The Event Bus manages what happens
	 * 
	 * @author Benno Kruit
	 */
	private static EventBus master;
	private HashSet<Listener> eventListeners;

	public static EventBus getInstance() {
		if (null == master) {
			master = new EventBus();
		}
		return master;
	}
	
	private EventBus() {
		eventListeners = new HashSet<Listener>();
	}
	public void addListener(Listener l){
		if (!eventListeners.contains(l)) {
			eventListeners.add(l);
		}
	}
	public void removeListener(Listener l){
		if (eventListeners.contains(l)) {
			eventListeners.remove(l);
		}
	}
	
	public void event(EventObject e) {
		for(Listener l : eventListeners) {
			// Listen up, this is special stuff and maybe it's bad but it works
			try {
				// We try to cast this event into whatever the listener might want
				l.event(e);
			} catch (Exception ex) {
				// If it doesn't want our event, no problem!
			}
		}
	}
}
