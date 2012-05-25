package events;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Sjoerd Dost
 *
 */

public class PictureEvent extends Event {
	
	private BufferedImage image;
	
	public PictureEvent(BufferedImage img) {
		this.image = img;
	}	
	
	public BufferedImage getImage()
	{
		return image;
	}
}
