package bots;

import java.util.EventObject;

import lib.FaceDetection2;
import mennov1.EventBus;

import com.googlecode.javacv.cpp.opencv_core.IplImage;

import events.Event;
import events.OpenCVPictureEvent;
import events.ReceiveChatEvent;
import events.SendChatEvent;

/**
 * Als het gevraagd wordt, zoek naar gezichten en zeg het als er iemand gevonden wordt
 * 
 * @author benno
 */

public class ZieJeIemand implements IBot<Event> {

	private ReceiveChatEvent kijkend_voor;
	private FaceDetection2 detection;

	public ZieJeIemand() {
		kijkend_voor = null;
		detection = new FaceDetection2();
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof OpenCVPictureEvent) || (e instanceof ReceiveChatEvent);
	}

	@Override
	public void event(Event e) {
		if (e instanceof ReceiveChatEvent && null == kijkend_voor) {
			// Als je nog niet kijkt en iemand vraagt je te kijken, kijk dan
			ReceiveChatEvent chat_event = (ReceiveChatEvent) e;
			if (chat_event.message.equals("zie je iemand?")) {
				kijkend_voor = chat_event;
				String response = "even kijken...";
				EventBus.getInstance().event(new SendChatEvent(this, kijkend_voor.client, kijkend_voor.sender, response));
			}
		}
		if (e instanceof OpenCVPictureEvent && null != kijkend_voor) {
			// Detecteer gezichten en antwoord
			IplImage origImg = ((OpenCVPictureEvent) e).getIplImage();
			int n = detection.findFaces(origImg).size();
			String response = (n > 0)? "ja, ik zie "+((n == 1)? "iemand" : n+" gezichten")+"!" : "nee, ik zie niemand...";
			EventBus.getInstance().event(new SendChatEvent(this, kijkend_voor.client, kijkend_voor.sender, response));
			kijkend_voor = null;
		}
	}

}
