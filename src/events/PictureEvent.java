package events;

import java.awt.image.BufferedImage;

/**
 * 
 * @author Sjoerd Dost
 *
 */

public class PictureEvent extends Event {
	
	private BufferedImage image;
	
	public PictureEvent(Object source, BufferedImage img) {
		super(source);
		this.image = img;
	}	
	
	public BufferedImage getImage()
	{
		return image;
	}
}
