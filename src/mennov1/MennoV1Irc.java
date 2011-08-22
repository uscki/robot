package mennov1;

import org.jibble.pircbot.PircBot;

public class MennoV1Irc extends PircBot {

	/**
	 * 
	 */
	public MennoV1Irc() {
		this.setName("MennoV1");
	}
	
	public void onMessage(String channel, String sender,
			String login, String hostname, String message) {
		String[] outputs = MennoV1.getInstance().parseArguments(message, sender);
		for(String s : outputs) {
			if(null == s){
				continue;
			}
			sendMessage(channel, s);
		}
	}

}
