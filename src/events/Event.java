public abstract class Event {
	public String info;
	public long timestamp;
	//TODO: nice methods for timestamp and such

	public abstract  String getEventInterface() {
		"ListensTo"+this.getClass().getSimpleName();
	}
}
