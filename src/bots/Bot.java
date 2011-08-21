package bots;

/**
 * 
 * @author Vincent Tunru
 * @category Framework
 * 
 * Bot interface, bots used by MennoV1 need to implement this.
 */
public interface Bot {
	public abstract String ask(String input);
}