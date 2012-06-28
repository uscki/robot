package bots;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import lib.FaceDetection;
import lib.ImageLib;

import events.Event;
import events.Response;
import events.PictureEvent;
import events.TextEvent;

/**
 * 
 * @author Sjoerd Dost
 * Drawing a mustache on a recognised face without Processing
 *
 */

public class SnorBot2 implements IBot {
	
	public Response handleEvents(Event event) {

		if(event instanceof TextEvent) {
			Response response = new Response();
			if(event.info.toLowerCase().startsWith("snor"))
			{
				//draw mustache
				//TODO hier willen we een link naar image van webcam
				BufferedImage img = ImageLib.loadImageFromWeb("http://www.messagefrommasters.com/Osho/images/Osho-on-adolf-hitler(1).jpg");
				
				List<Rectangle> faceList = FaceDetection.findFaces(img, 1, 40);
				
				BufferedImage snor = ImageLib.loadImage("snor.png");
				
				for(Rectangle face : faceList){
					
					int x = face.x + (int)(0.25 * face.width);
					int y = face.y + (int)(0.65 * face.height);
					
					double sx = (face.width * 0.5) / snor.getWidth();
					double sy = (face.height * 0.25) / snor.getHeight();
					
					snor = ImageLib.scale(snor, sx, sy);
					
					ImageLib.drawImage(img,snor,x,y);
				}
				
				if(img == null){
					System.out.println("oops");
				} else {
					ImageLib.saveImage(img,"webcamtest.jpg");
				}
				
				//add PictureEvent
				PictureEvent pica = new PictureEvent(img);
				response.addEvent(pica);
			}
			return response;
		}
		
		return new Response();
	}
	
}
