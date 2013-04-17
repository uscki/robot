package mennov1.commands;

import mennov1.EventBus;

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

			if (EventBus.getInstance().removeAllBots(args[1])) {
				// Remove the bot to out event bus
				return "The bot " + args[1] + " was unloaded";
			} else {
				return "Nothing could be unloaded";
			}
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
