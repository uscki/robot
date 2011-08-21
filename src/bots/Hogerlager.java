package bots;

import java.util.Hashtable;

public class Hogerlager implements Bot {

	protected Hashtable<String, Integer> getallen;
	
	public Hogerlager() {
		getallen = new Hashtable<String, Integer>();
	}

	public String ask(String input, String user) {
		if(input.contains("hoger lager spelen")){
			getallen.put(user, (int) (10 * Math.random()) + 1);
			return "Goed, we spelen hoger lager. Ik heb een getal bedacht (groter dan 0, kleiner of gelijk aan 10), begin maar te raden " + user + "!";
		} else if(input.startsWith("Is het ")) {
			// Alleen spelen als de speler een spelletje is begonnen
			if(!getallen.containsKey(user)){
				return null;
			}
			
			int geraden;
			try{
				geraden = Integer.parseInt(input.substring(7, input.length()));
			} catch(NumberFormatException e) {
				return null;
			}
			
			if(geraden == getallen.get(user)){
				getallen.remove(user);
				return "Gefeliciteerd, je hebt het geraden! Het was inderdaad " + geraden + "."; 
			} else if(geraden < getallen.get(user)) {
				return "Helaas, het getal dat ik heb bedacht is hoger dan " + geraden + ".";
			} else if(geraden > getallen.get(user)) {
				return "Helaas, het getal dat ik heb bedacht is lager dan " + geraden + ".";
			}
		}
		
		return null;
	}

}
