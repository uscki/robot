package bots;

public class Trollbot extends AnswerBot{
	
	String[] trollWoorden = {"hard", "nat", "stijf", "lang"};
	
	@Override
	public String ask(String in, String who) {
		if(CheckOpTrollbaarheid(in)){
			return "Dat zei mijn vrouw ook vannacht!";
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
