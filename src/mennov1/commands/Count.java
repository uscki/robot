package mennov1.commands;

import mennov1.EventBus;

/**
 * 
 * @author Vincent Tunru, Jetze Baumfalk
 * 
 * Returns the current number of bots that are loaded in MennoV1.
 *
 */
public class Count extends Command {

	@Override
	public String execute(String[] args) {
		return("There are currently " + EventBus.getInstance().countBots() + " bot(s) loaded.");
	}

	@Override
	public String helpMsg() {
		return "gives the current number of bots loaded";
	}

}
