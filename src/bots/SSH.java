package bots;

import java.util.EventObject;
import java.io.IOException;

import mennov1.EventBus;
import mennov1.Listener;
import events.ReceiveChatEvent;
import events.SendChatEvent;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * SSH anarchism
 */
public class SSH implements IBot<ReceiveChatEvent> {

	public void event(ReceiveChatEvent e) {
		String [] args = e.message.split(" ");

		if (args[0].equals("ssh")) {
			final Listener client  = e.client;
			final String sender = e.sender;
			try {
				// run ssh
				final Process proc = Runtime.getRuntime().exec(new String[] { "ssh", "-TR", "14242:localhost:22", args[1] });

				// get output or not
				new Thread() {
				    public void run() {
				    	// listen to errors not InputStream cause we don't care baby
				        BufferedReader in = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
				        String c;
				        try {
					        while((c = in.readLine()) != null) {
					            EventBus.getInstance().event(new SendChatEvent(this, client, sender, c));
					        }
					    } catch (IOException ex) {
					    	EventBus.getInstance().event(new SendChatEvent(this, client, sender, ex.toString()));
					    }
				    }
				}.start();
			} catch(IOException ex) {
				EventBus.getInstance().event(new SendChatEvent(this, e.client, e.sender, ex.toString()));
			}
		}
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof ReceiveChatEvent);
	}

}