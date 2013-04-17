package mennov1;

import java.io.File;
import java.util.EventObject;

import javax.imageio.ImageIO;

import events.PictureEvent;

/**
 * 
 * Listens to the 'modified' meta-info of a file, and raises a picture event if it changes with the new file
 * @todo It doesn't have to be a Listener
 *
 */

public class ImageFileClient implements Listener<PictureEvent> {

	private String fname;
	private long delay;

	public ImageFileClient(String fname, long delay) {
		this.fname = fname;
		this.delay = delay;
		Thread t = new Thread(new FileListener());
		t.start();
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof PictureEvent);
	}

	@Override
	public void event(PictureEvent e) {
		
	}

	private class FileListener implements Runnable {
		public void run() {
			try {
				File f = new File(fname);
				long m = f.lastModified();
				while (true) {
					if (m != f.lastModified()) {
						m = f.lastModified();
						
						EventBus.getInstance().event(new PictureEvent(this, ImageIO.read(f)));
						continue;
					}
					try {
						Thread.sleep(delay);
					} catch (InterruptedException x) {
						Thread.currentThread().interrupt();
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
