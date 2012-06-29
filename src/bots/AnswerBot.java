package bots;

import java.util.EventObject;

import mennov1.EventBus;
import events.ReceiveChatEvent;
import events.SendChatEvent;

public abstract class AnswerBot implements IBot<ReceiveChatEvent> {
	public void event(ReceiveChatEvent e) {
		// Answer to the same chat session
		String response = this.ask(e.message, e.sender);
		if (null != response || response == "") {
			EventBus.getInstance().event(new SendChatEvent(this, e.client, e.sender, response));
		}
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof ReceiveChatEvent);
	}
	
	public abstract String ask(String in, String who);
}
