package mennov1;

import org.jibble.pircbot.*;

public class IrcClient {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		MennoV1Irc bot = new MennoV1Irc();
		
		bot.connect("irc.enterthegame.com");
		bot.joinChannel("#incognito");
	}

}
