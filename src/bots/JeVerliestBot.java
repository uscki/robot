package bots;

import mennov1.EventBus;
import events.JeVerliestEvent;

public class JeVerliestBot extends AnswerBot {
	
	public JeVerliestBot() {
		EventBus.getInstance().addListener(new HahaBot());
	}
	
	public String ask(String in, String user) {
		if(in.equals("loser")) {
			EventBus.getInstance().event(new JeVerliestEvent(this, "Menno V1", user, 10));
			return "Haha, verloren!";
		}
		else
			return null;
	}
}
