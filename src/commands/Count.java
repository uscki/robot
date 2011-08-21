package commands;

import mennov1.MennoV1;

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
		return("There are currently " + MennoV1.getInstance().listenerBots.size() + " bot(s) loaded.");
	}

	@Override
	public String helpMsg() {
		return "gives the current number of bots loaded";
	}

}
