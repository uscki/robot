package mennov1;
import org.jibble.pircbot.*;

import events.ReceiveChatEvent;
import events.SendChatEvent;

public class IrcClient extends PircBot implements Listener<SendChatEvent> {

	private static IrcClient master;
	
	public static IrcClient getInstance() {
		if (null == master) {
			master = new IrcClient();
		}
		return master;
	}
	
	private IrcClient() {
		this.setName("MennoV1");
		try {
			this.connect("irc.enterthegame.com");
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.joinChannel("#incognito");
	}
	
	public void onMessage(String channel, String sender, String login, String hostname, String message) {
		EventBus.getInstance().event(new ReceiveChatEvent(this, this, channel, message));
	}
	
	protected void onPrivateMessage(String sender, String login, String hostname, String message) {
		EventBus.getInstance().event(new ReceiveChatEvent(this, this, sender, message));
	}
	
	public void event(SendChatEvent e) {
		if (this == e.client) {
			sendMessage(e.receiver, e.message);
		}
	}

}
