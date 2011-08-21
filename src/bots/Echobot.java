package bots;

/**
 * 
 * @author Vincent Tunru
 * @category Bots
 *
 * A very simple example bot who is basically a parrot.
 */
public class Echobot implements Bot{

	public String ask(String input) {
		return input;
	}
}
