package bots;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.EventObject;


import lib.FaceDetection2;

import lib.ImageLib;
import mennov1.EventBus;
import events.*;

import com.googlecode.javacv.cpp.opencv_core.IplImage;


/**
 * 
 * @author Sjoerd Dost
 * Drawing a mustache on a recognised face without Processing
 *
 */

public class SnorBot implements IBot<Event> {

	private ReceiveChatEvent snor_voor;
	private FaceDetection2 detection;

	public SnorBot() {
		snor_voor = null;
		detection = new FaceDetection2();
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof OpenCVPictureEvent) || (e instanceof ReceiveChatEvent);
	}

	@Override
	public void event(Event e) {
		if (e instanceof ReceiveChatEvent && null == snor_voor) {
			// Als je nog niet kijkt en iemand vraagt je te kijken, kijk dan
			ReceiveChatEvent chat_event = (ReceiveChatEvent) e;
			if (chat_event.message.startsWith("snor")) {
				snor_voor = chat_event;
				String response = "even kijken...";
				EventBus.getInstance().event(new SendChatEvent(this, snor_voor.client, snor_voor.sender, response));
			}
		}
		if (e instanceof OpenCVPictureEvent && null != snor_voor) {
			// Detecteer gezichten en antwoord
			OpenCVPictureEvent img_event = (OpenCVPictureEvent) e;
			IplImage origImg = img_event.getIplImage();
			BufferedImage img = img_event.getImage();

			List<Rectangle> faceList = detection.findFaces(origImg);

			BufferedImage snor = ImageLib.loadImage("snor.png");

			for(Rectangle face : faceList) {

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
				ImageLib.saveImage(img,"snorhoofdjes.jpg");
			}

			String response = (faceList.size() > 0)? "haha, snor!" : "ik zie niemand :(";
			EventBus.getInstance().event(new SendChatEvent(this, snor_voor.client, snor_voor.sender, response));

			snor_voor = null;
		}
	}

}
