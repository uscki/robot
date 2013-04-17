package mennov1.commands;

import mennov1.EventBus;

/**
 * 
 * @author Vincent Tunru, Jetze Baumfalk
 * 
 * Returns the current number of bots that are loaded in MennoV1.
 *
 */
public class List extends Command {

	@Override
	public String execute(String[] args) {
		return("Currenty loaded bots " + EventBus.getInstance().listBots() + ".");
	}

	@Override
	public String helpMsg() {
		return "gives a list of loaded bots";
	}

}
