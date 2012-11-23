package bots;

public class Trollbot extends AnswerBot{
	
	String[] trollWoorden = {"hard", "nat", "stijf", "lang", "vol", "vochtig", "stijve", "anjer", "bestijgen", "climax", "genot", "eikel", "harig", "hoogtepunt", "kwakje", "paal", "palen", "pijpen", "trekken", "sjorren"};
	
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
