package mennov1;
import java.util.EventListener;
import java.util.EventObject;

import events.Event;


public interface Listener<SubEvent extends EventObject> extends EventListener {
	public void event(SubEvent e);
}
