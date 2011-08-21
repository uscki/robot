package nl.uscki.robot.mennov1;

public abstract class Command {
	
	public abstract String execute(String [] args);
	public abstract String helpMsg();
}
