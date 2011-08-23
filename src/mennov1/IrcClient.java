package mennov1;

import org.jibble.pircbot.*;

public class IrcClient extends PircBot {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		IrcClient bot = new IrcClient();
		
		bot.connect("irc.enterthegame.com");
		bot.joinChannel("#incognito");
	}
	
	public IrcClient() {
		this.setName("MennoV1");
	}

	public void onMessage(String channel, String sender,
			String login, String hostname, String message) {
		System.out.println("Received message from " + sender + " in " + channel + ": " + message);
		String[] outputs = MennoV1.getInstance().parseArguments(message, sender);
		for(String s : outputs) {
			if(null == s){
				continue;
			}
			System.out.println("Sent message: " + s);
			sendMessage(channel, s);
		}
	}

}
