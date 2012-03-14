package events;

public class TextEvent extends Event {
	public TextEvent(String info, String who) {
		this.info = info;
		this.who = who;
	}	
}
