package bots;

import java.io.IOException;
import java.util.EventObject;

import mennov1.EventBus;
import events.ReceiveChatEvent;
import events.SendChatEvent;

public class Reboot implements IBot<ReceiveChatEvent> {
	@Override
	public void event(ReceiveChatEvent e) {
		// Answer to the same chat session
		String in = e.message;
		if (in.equals("samsara")) { // reboot
			try {
				say(e, "From an inconstruable beginning comes transmigration. A beginning point is not evident, though beings hindered by ignorance and fettered by craving are transmigrating & wandering on. What do you think, monks: Which is greater, the tears you have shed while transmigrating & wandering this long, long time - crying & weeping from being joined with what is displeasing, being separated from what is pleasing - or the water in the four great oceans?");
				Runtime.getRuntime().exec("shutdown -r 1");
				say(e, "/quit");
			} catch(IOException ex) {}
		} else if (in.equals("biecht")) { // poop log to server
			try {
				say(e, "Ego te absolvo a peccatis tuis in nomine Patris et Filii et Spiritus Sancti. Amen.");
				Runtime.getRuntime().exec("echo \"log=`sed 's/$/\\<BR\\>/' /home/mennov1/start-menno.log`\" | curl -d @- https://robot.uscki.nl/log/log.php");
			} catch(IOException ex) {}
		}
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof ReceiveChatEvent);
	}
	
	private void say(ReceiveChatEvent e, String s) {
		EventBus.getInstance().event(new SendChatEvent(this, e.client, e.sender, s));
	}

}
