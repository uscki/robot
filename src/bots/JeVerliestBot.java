package bots;

import mennov1.EventBus;
import events.JeVerliestEvent;
import events.Response;

public class JeVerliestBot extends AnswerBot {
	public String ask(String in, String user) {
		if(in.equals("loser")) {
			EventBus.getInstance().event(new JeVerliestEvent(this, "Menno V1", user, 10));
		}
		return "Haha, verloren!";
	}
}
