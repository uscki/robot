package events;

public class JeVerliestEvent extends Event {
	public String winnaar;
	public String verliezer;
	public int score;

	public JeVerliestEvent(Object source, String winnaar, String verliezer, int score) {
		super(source);
		this.winnaar = winnaar;
		this.verliezer = verliezer;
		this.score = score;
	}	
}	


