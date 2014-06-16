package mennov1;

import java.io.IOException;

import library.SewerSender;

/**
 * 
 * The main class for the robot. This initializes the communication channels (clients) to the bothandler.
 *
 */

public class MennoV1 {

	public static void main(String[] args) {

		EventBus bus = EventBus.getInstance();
		bus.addListener(BotHandler.getInstance());
//		bus.addListener(FacebookClient.getInstance());
		bus.addListener(JabberClient.getInstance());
//		bus.addListener(IrcClient.getInstance());
		bus.addListener(TerminalClient.getInstance());
		System.out.println(System.getProperty("java.library.path"));

//		try {
//			Thread t = new Thread(new WebcamClient());
//			t.start();
//		} catch (Exception e) {
//			SewerSender.println("webcam kapot huilen!\n"+e.toString());
//		}

//		bus.addListener(new ImageUploader("https://robot.uscki.nl/webcam.php"));

		// Send a sign of life to robot.uscki.nl
		System.out.println(SewerSender.sentLifeSign()? "Sent life sign" : "Life sign failed");

		try {
			System.in.read();
		} catch (IOException ex) {
			//ex.printStackTrace();
		}
		// TODO this code is actually never reached...
		JabberClient.getInstance().disconnect();
		FacebookClient.getInstance().disconnect();
	}
}
