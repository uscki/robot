package bots;

import java.util.EventObject;

import mennov1.EventBus;
import mennov1.IrcClient;

import events.Event;
import events.JeVerliestEvent;
import events.Response;
import events.SendChatEvent;

public class HahaBot implements IBot<JeVerliestEvent> {
	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof JeVerliestEvent);
	}

	@Override
	public void event(JeVerliestEvent e) {
		EventBus.getInstance().event(new SendChatEvent(this, IrcClient.getInstance(), "#incognito", "Je zuigt, " + e.verliezer + "!"));	
	}
}
