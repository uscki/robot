package library;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

/**
 * 
 * @author Sjoerd Dost
 * The Image-library uses Buffered Images and Graphics2D
 * to load, modify and save images in jpg or png format
 *
 */

public class ImageLib {
	
	/**
	 * 
	 * Draw a rectangle at (x,y) in image img
	 * @param img	The image to be drawn in
	 * @param c		The color of the rectangle
	 * @param x		The x-coordinate of the rectangle's upper-left corner
	 * @param y		The y-coordinate of the rectangle's upper-left corner
	 * @param w		The width of the rectangle
	 * @param h		The height of the rectangle
	 */
	public static void drawRectangle(BufferedImage img, Color c, int x, int y, int w, int h) {
		Graphics2D g2d = img.createGraphics();				
		g2d.setColor(c);
		g2d.fill(new Rectangle2D.Float(x, y, w, h));
		g2d.dispose();
	}
	
	/**
	 * 
	 * Draw an ellipse at (x,y) in image img
	 * 
	 */
	public static void drawEllipse(BufferedImage img, Color c, int x, int y, int w, int h) {
		Graphics2D g2d = img.createGraphics();				
		g2d.setColor(c);
		g2d.fill(new Ellipse2D.Float(x, y, w, h));
		g2d.dispose();
	}
	
	/**
	 * 
	 * Draw an image (target) at (x,y) in another image (object)
	 * 
	 */
	public static void drawImage(BufferedImage object_img, BufferedImage target_img, int x, int y) {
		Graphics2D g2d = object_img.createGraphics();				
		g2d.drawImage(target_img,x,y,null);
		g2d.dispose();
	}
	
	/**
	 * 
	 * Return a scaled version of an image
	 * @param sx	scale in x
	 * @param sy	scale in y
	 * 
	 */
	public static BufferedImage scale(BufferedImage img, double sx, double sy) {
		BufferedImage scaledImage = new BufferedImage((int)(sx * img.getWidth()),(int)(sy * img.getHeight()), BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = scaledImage.createGraphics();
		AffineTransform xform = AffineTransform.getScaleInstance(sx, sy);
		graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		graphics2D.drawImage(img, xform, null);
		graphics2D.dispose();
		return scaledImage;
	}

	/**
	 *
	 * @param img	The BufferedImage to save 
	 * @param ref	Pathname, ending in .png or .jpg
	 */
	public static void saveImage(BufferedImage img, String ref) {  
	    try {  
	        String format = (ref.endsWith(".png")) ? "png" : "jpg";  
	        ImageIO.write(img, format, new File(ref));  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	/**
	 * 
	 * Loading a local image
	 * @param pathname
	 * @return
	 */
	public static BufferedImage loadImage(String pathname) {
		BufferedImage img = null;
		try {
		    img = ImageIO.read(new File(pathname));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	/**
	 * 
	 * Loading an image from the web
	 * @param url
	 * @return
	 */
	public static BufferedImage loadImageFromWeb(String url) {
		BufferedImage img = null;
		try {
			   URL u = new URL(url);
			   img = ImageIO.read(u);
			} catch (IOException e) {
			}
		return img;
	}

	public static void maakGif(String string, int i, int j) {
		// TODO Auto-generated method stub
		
	}
	
}
