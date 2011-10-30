package commands;

import mennov1.BotHandler;

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
			if(BotHandler.getInstance().listenerBots.remove(args[1]) == null)
				return "The bot didn't exist, so it couldn't be unloaded either.";
			else
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
