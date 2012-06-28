package mennov1;
import java.util.Collections;
import java.util.EventObject;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;


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
	public void removeAllListeners(String name){
		for(Listener l : eventListeners.keySet()) {
			if (name.equals(l.getClass().getSimpleName())) {
				eventListeners.remove(l);
			}
		}
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
