package mennov1;

import java.io.IOException;
import java.util.ArrayList;

import org.jibble.pircbot.*;

public class IrcClient extends PircBot implements Runnable {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		IrcClient bot = new IrcClient();
		try {
			bot.connect("irc.enterthegame.com");
		} catch (NickAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bot.joinChannel("#incognito");
	}
	
	public IrcClient() {
		this.setName("MennoV1");
	}

	public void onMessage(String channel, String sender,
			String login, String hostname, String message) {
		System.out.println("Received message from " + sender + " in " + channel + ": " + message);
		ArrayList <String> outputs = BotHandler.getInstance().parseArguments(message, sender);
		for(String s : outputs) {
			if(null == s){
				continue;
			}
			System.out.println("Sent message: " + s);
			sendMessage(channel, s);
		}
	}
	
	protected void onPrivateMessage(String sender, String login,
			String hostname, String message) {
		System.out.println("Received private message from " + sender + ": " + message);
		ArrayList <String> outputs = BotHandler.getInstance().parseArguments(message, sender);
		for(String s : outputs) {
			if(null == s){
				continue;
			}
			System.out.println("Sent message: " + s);
			sendMessage(sender, s);
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		IrcClient bot = new IrcClient();
		
		try {
			bot.connect("irc.enterthegame.com");
		} catch (NickAlreadyInUseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IrcException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		bot.joinChannel("#incognito");
	}

}
