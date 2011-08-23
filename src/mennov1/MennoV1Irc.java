package mennov1;

import org.jibble.pircbot.PircBot;

public class MennoV1Irc extends PircBot {

	/**
	 * 
	 */
	public MennoV1Irc() {
		this.setName("MennoV1");
	}
	
	
	
	@Override
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		// TODO Auto-generated method stub
		super.onPrivateMessage(sender, login, hostname, message);
		String[] outputs = MennoV1.getInstance().parseArguments(message, sender);
		for(String s : outputs) {
			if(null == s){
				continue;
			}
			sendMessage(sender, s);
		}
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
