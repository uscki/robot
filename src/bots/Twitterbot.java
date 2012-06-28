package bots;

import java.util.EventObject;

import events.ReceiveChatEvent;
import lib.TwitterLib;
import mennov1.Listener;

public class Twitterbot implements Listener<ReceiveChatEvent> {
	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof ReceiveChatEvent);
	}

	@Override
	public void event(ReceiveChatEvent e) {
		if(e.message.startsWith("shout ")) {
			TwitterLib.getInstance().tweet(e.message.substring(6));
		}
	}

}
