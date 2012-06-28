package mennov1;


import java.io.IOException;

/**
 * 
 * @category Framework
 * 
 * The main class for the robot. This initializes the communication channels (clients) to the bothandler.
 *
 */

public class MennoV1 {
	
	public static void main(String[] args) {
		
		EventBus bus = EventBus.getInstance();
		bus.addListener(BotHandler.getInstance());
		bus.addListener(JabberClient.getInstance());
		bus.addListener(IrcClient.getInstance());
		bus.addListener(new ImageFileClient("test.jpeg", 1000L));
		bus.addListener(TerminalClient.getInstance());
		
		
		try {
			System.in.read();
		} catch (IOException ex) {
			//ex.printStackTrace();
		}
		JabberClient.getInstance().disconnect();
	}
}