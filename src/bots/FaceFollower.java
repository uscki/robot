package bots;

import java.util.EventObject;
import events.PictureEvent;

public class FaceFollower implements IBot<PictureEvent> {

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof PictureEvent) && null != e;
	}

	@Override
	public void event(PictureEvent e) {
		System.out.println("PictureEvent: " + e);
	}



}
