public class JeVerliestEvent extends Event {
	String winnaar;
	String verliezer;
	int score;

	public JeVerliestEvent(String winnaar, String verliezer, score) {
		this.winnaar = winnaar;
		this.verliezer = verliezer;
		this.score = score;
	}	
}	

public interface ListensToJeVerliestEvent {

}
