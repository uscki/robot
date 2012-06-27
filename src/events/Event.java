package events;

import java.util.Calendar;
import java.util.EventObject;

public abstract class Event extends EventObject {
	public Event(Object source) {
		super(source);
		timestamp = Calendar.getInstance().getTimeInMillis();
	}

	public String info;

	public long timestamp;
}
