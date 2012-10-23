import bots.AnswerBot;

public class Tettenbot extends AnswerBot {

	@Override
	public String ask(String input, String user) {
		if(input.substring(input.length()-5,input.length()).equals("etten"))
			return "Haha, dat rijmt op tetten!";
		return null;
	}
}
