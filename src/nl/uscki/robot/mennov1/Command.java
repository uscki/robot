package nl.uscki.robot.mennov1;

public abstract class Command {
	
	public abstract int execute(String [] args);
	public abstract String helpMsg();
}
