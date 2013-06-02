package mennov1;

import java.io.File;
import java.util.EventObject;
import javax.imageio.ImageIO;

import library.SewerSender;
import events.PictureEvent;

public class ImageUploader implements Listener<PictureEvent> {

	private String fname;
	private String url;

	public ImageUploader(String fname, String url) {
		this.fname = fname;
		this.url = url;
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof PictureEvent);
	}

	@Override
	public void event(PictureEvent e) {
		// Sla het plaatje op en upload het dan via curl
		try {
			if (null != fname) {
				ImageIO.write(e.getImage(), "jpg", new File(fname));
				if (null != url) {
					// TODO Uploaden via java ipv curl
					Process process = Runtime.getRuntime().exec(new String[] { "webcam/upload", fname, url });
					if (0 != process.waitFor()) {
						SewerSender.println("Uploading exited weird!");
					}
					process.destroy();
				}
			}
		} catch (Exception ex) { SewerSender.println(ex.toString()); }
	}

}
