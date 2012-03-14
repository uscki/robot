package events;

public class JeVerliestEvent extends Event {
	public String winnaar;
	public String verliezer;
	public int score;

	public JeVerliestEvent(String winnaar, String verliezer, int score) {
		this.winnaar = winnaar;
		this.verliezer = verliezer;
		this.score = score;
	}	
}	


