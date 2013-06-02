package bots;

import library.ImageLib;

public class Gifbot extends AnswerBot {
	
	public String ask(String input, String user) {
		if (input.contains("gif")) {
			ImageLib.maakGif("gifbot.gif",10,3);
			return "Gif gemaakt";
		}
		return null;
	}

}
