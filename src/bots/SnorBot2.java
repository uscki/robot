package bots;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;

import lib.FaceDetection;
import lib.ImageLib;
import mennov1.EventBus;
import events.PictureEvent;

/**
 * 
 * @author Sjoerd Dost
 * Drawing a mustache on a recognised face without Processing
 *
 */

public class SnorBot2 extends AnswerBot {

	public String ask(String in, String who) {
		if (in.startsWith("snor"))
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
			EventBus.getInstance().event(new PictureEvent(this, img));
			return "Haha snor";
		}
		return null;
	}

}
