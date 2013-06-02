package bots;


/**

*/
public class QuoteBot extends AnswerBot {
	
	public String ask(String input, String user) {
		if(!(input.contains("Wat zou ") && input.contains(" zeggen")))
			return null;
		
		// kijk of x bestaat
		int beginNameLoc = "Wat zou ".length();
		int endNameLoc = input.length() - " zeggen".length();
		/*Person x = USCKI.getPerson(input.substring(beginNameLoc,endNameLoc);
		
		//bestaat x niet?
		if(x == null)
			return "Helaas, " + x + " ken ik niet!";
			
			
		return x.generateQuote();*/
		return input.substring(beginNameLoc,endNameLoc) + " Currently out of order, please wait";
	}
}
