package bots;

import java.awt.image.BufferedImage;

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
				//TODO draw mustache
				BufferedImage img = null;
				
				//add PictureEvent
				PictureEvent pica = new PictureEvent(img);
				response.addEvent(pica);
			}
			return response;
		}
		
		return new Response();
	}
	
}
