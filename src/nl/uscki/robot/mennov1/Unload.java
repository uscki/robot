package nl.uscki.robot.mennov1;

public class Unload extends Command {
	
	@Override
	public String execute(String [] args) {
		return("Woei, in unload!");
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName()+ ": unloads a certain modue";
	}

}
