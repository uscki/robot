package commands;


import mennov1.EventBus;

/**
 * 
 * @author Benno Kruit
 *
 */
public class Clients extends Command {
	
	@Override
	public String execute(String [] args) {
		return("Currenty loaded bots " + EventBus.getInstance().listClients() + ".");
		
	}

	@Override
	public String helpMsg() {
		return "lists loaded clients";
	}

}
