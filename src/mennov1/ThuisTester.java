package mennov1;

import java.io.IOException;

import lib.SewerSender;

/**
 * 
 * @category Framework
 * 
 * The home testing class for the robot. This initializes a subset of the communication channels (clients) to the bothandler.
 *
 */

public class ThuisTester {
	
	public static void main(String[] args) {
		
		EventBus bus = EventBus.getInstance();
		bus.addListener(BotHandler.getInstance());
		//bus.addListener(JabberClient.getInstance());
		//bus.addListener(IrcClient.getInstance());
		//bus.addListener(new ImageFileClient("/home/mennov1/webcam/webcam.jpg", 1000L));
		bus.addListener(TerminalClient.getInstance());
		
		// try {
		// 	Thread t = new Thread(new WebcamClient());
		// 	t.start();
		// } catch (Exception e) {
  //       	SewerSender.println("webcam kapot huilen!\n"+e.toString());
  //       }
		
		// Send a sign of life to robot.uscki.nl
		//System.out.println(SewerSender.sentLifeSign()? "Sent life sign" : "Life sign failed");
		
		try {
			System.in.read();
		} catch (IOException ex) {
			//ex.printStackTrace();
		}
		//JabberClient.getInstance().disconnect();
	}
}
