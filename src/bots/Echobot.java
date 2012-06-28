package bots;

/**
 * 
 * @author Vincent Tunru
 * @category Bots
 *
 * A very simple example bot who is basically a parrot.
 */
public class Echobot extends AnswerBot {
	public String ask(String in, String who) {
		return in;
	}
}
