package mennov1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EventObject;

import events.ReceiveChatEvent;
import events.SendChatEvent;

/**
 * 
 * @author Jetze Baumfalk, Vincent Tunru
 * @category Framework
 * 
 * A basic terminal that can be used to communicate with MennoV1.
 */
public class TerminalClient implements Runnable, Listener<SendChatEvent> {
	
	private static TerminalClient master;
	String name;

	public static TerminalClient getInstance() {
		if (null == master) {
			master = new TerminalClient();
			Thread t = new Thread(master);
			t.start();
		}
		return master;
	}
	
	@Override
	public void run() {
		name = "Termy";
		System.out.println("Menno V1 Terminalclient: your name is termy");
		while(true) {
			try {
				BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
				String readLine = buffy.readLine();

				EventBus.getInstance().event(new ReceiveChatEvent(master, master, name, readLine));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				//System.out.println("In the end, it doesn't even matter");
			}
		}
	}

	@Override
	public void event(SendChatEvent e) {
		System.out.println(e.receiver + ": " + e.message);		
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof SendChatEvent);
	}
}
