package bots;

import java.io.IOException;
import java.util.EventObject;

import mennov1.EventBus;
import mennov1.IrcClient;

import events.Event;
import events.Response;
import events.SendChatEvent;


import mennov1.EventBus;

public class Reboot extends AnswerBot {

	@Override
	public String ask(String in, String who) {
		if (in.equals("samsara")) {
			try {
					Runtime.getRuntime().exec("shutdown -r 1");
					EventBus.getInstance().event(new SendChatEvent(this, IrcClient.getInstance(), "#incognito", "From an inconstruable beginning comes transmigration. A beginning point is not evident, though beings hindered by ignorance and fettered by craving are transmigrating & wandering on. What do you think, monks: Which is greater, the tears you have shed while transmigrating & wandering this long, long time - crying & weeping from being joined with what is displeasing, being separated from what is pleasing - or the water in the four great oceans?"));
					return "/quit" ;
			} catch(IOException e) {
				return e.toString();
			}
		} else {
			return null;
		}
	}

}
