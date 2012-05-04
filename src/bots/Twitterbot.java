package bots;

import events.Event;
import events.Response;
import events.TextEvent;
import lib.TwitterLib;

public class Twitterbot implements IBot {
	
	private TwitterLib twitter = new TwitterLib();
	
	@Override
	public Response handleEvents(Event event) {

		if(event instanceof TextEvent) {
			twitter.tweet(event.info);
		}
		
		return new Response();
	}

}
