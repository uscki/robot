package bots;

public class Trollbot implements Bot {

	public String ask(String input, String user) {
		if(input.contains("hard") ||
		   input.contains("nat") ||
		   input.contains("stijf") ||
		   input.contains("lang")){
			return "Dat zei mijn vrouw ook vannacht!";
		}
		
		return null;
	}

}
