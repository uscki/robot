package mennov1;

import java.io.File;
import java.util.EventObject;
import javax.imageio.ImageIO;

import library.SewerSender;
import events.PictureEvent;

public class ImageSaver implements Listener<PictureEvent> {

	private String fname;

	public ImageSaver(String fname, String url) {
		this.fname = fname;
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof PictureEvent);
	}

	@Override
	public void event(PictureEvent e) {
		// Sla het plaatje op
		try {
			if (null != fname) {
				ImageIO.write(e.getImage(), "jpg", new File(fname));
			}
		} catch (Exception ex) { SewerSender.println(ex.toString()); }
	}

}
