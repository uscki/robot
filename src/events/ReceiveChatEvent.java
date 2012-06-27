package events;
import java.util.EventObject;

import mennov1.Listener;

public class ReceiveChatEvent extends Event {
	
	public String sender;
	public String message;
	public Listener client;
	
	public ReceiveChatEvent(Object source, Listener client, String sender_name, String message) {
		super(source);
		this.client = client;
		this.sender = sender_name;
		this.message = message;
	}
}
