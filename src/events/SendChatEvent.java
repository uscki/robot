package events;
import mennov1.Listener;

public class SendChatEvent extends Event {

	public String message;
	public String receiver;
	public Listener client;
	
	public SendChatEvent(Object source, Listener client, String receiver, String message) {
		super(source);
		this.client = client;
		this.message = message;
		this.receiver = receiver;
	}

}
