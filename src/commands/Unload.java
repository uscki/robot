package commands;

import java.util.HashMap;

import mennov1.BotHandler;
import bots.Bot;

/**
 * 
 * @author Jetze Baumfalk
 *
 * Used to unload a certain bot.
 */
public class Unload extends Command {
	
	@Override
	public String execute(String [] args) {
		try
		{

			for(HashMap<String,Bot> botList : BotHandler.getInstance().subscriberList.values()) {
				botList.remove(args[1]);
			}
			return "The bot " + args[1] + " was unloaded";
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return "You need to supply the bot that needs to be unloaded";
		}
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return "unloads a certain module";
	}

}
