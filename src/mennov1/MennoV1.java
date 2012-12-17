package mennov1;


import java.io.IOException;

import lib.SewerSender;

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
        // JabberClient uitgecommentarieerd in hoop dat de webcam het dan doet
		//bus.addListener(JabberClient.getInstance());
		bus.addListener(IrcClient.getInstance());
		bus.addListener(new ImageFileClient("/home/mennov1/webcam/webcam.jpg", 1000L));
		bus.addListener(TerminalClient.getInstance());
		
		new WebcamClient();
		
		// Send a sign of life to robot.uscki.nl
		System.out.println(SewerSender.sentLifeSign()? "Sent life sign" : "Life sign failed");
		
		try {
			System.in.read();
		} catch (IOException ex) {
			//ex.printStackTrace();
		}
        // JabberClient uitgecommentarieerd in hoop dat de webcam het dan doet
		//JabberClient.getInstance().disconnect();
	}
}