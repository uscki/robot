package bots;

import java.awt.List;
import java.util.ArrayList;

import events.Event;
import events.Response;
import events.TextEvent;

public class Trollbot implements IBot{
	
	String[] trollWoorden = {"hard", "nat", "stijf", "lang"};
	
	@Override
	public Response handleEvents(Event event) {
		if(event instanceof TextEvent && CheckOpTrollbaarheid(event.info)){
			Response response = new Response();
			response.setResponse("Dat zei mijn vrouw ook vannacht!");
			return response;
		}
		return null;
	}

	private boolean CheckOpTrollbaarheid(String zin) {
		for(String s : trollWoorden){
			if(zin.contains(s)){
				return true;
			}
		}
		return false;
	}

}
