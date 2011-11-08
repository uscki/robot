package bots;

import lib.Looker;

public class Gifbot implements Bot {
	
	public String ask(String input, String user) {
		if (input.contains("gif")) {
			Looker.getInstance().maakGif("gifbot.gif",10,3);
			return "Gif gemaakt";
		}
		return null;
	}

}
