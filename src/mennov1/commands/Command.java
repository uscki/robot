package mennov1.commands;

/**
 * 
 * @author Jetze Baumfalk
 * @category Framework
 * Command interface, used to define commands that can be executed by MennoV1.
 */
public abstract class Command {
	
	public abstract String execute(String [] args);
	public abstract String helpMsg();
}
