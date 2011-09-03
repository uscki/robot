package bots;

import java.util.Hashtable;

import jpl.Atom;
import jpl.Query;
import jpl.Term;

public class WieIsHet implements Bot {

	protected Hashtable<String, Conversation> gesprekken;
	
	public WieIsHet() {
		gesprekken = new Hashtable<String, Conversation>();
		Query load = new Query( 
		        "consult", 
		        new Term[] {new Atom("wie-is-het2.pro")} 
		    );
		System.out.println( "Loading " + (load.query() ? "succeeded" : "failed"));
	}

	public String ask(String input, String user) {
		if (!gesprekken.containsKey(user)){
			// Een nieuw gesprek beginnen?
			if (input.contains("wie is het")) {
				Conversation newconf = new Conversation(user);
				gesprekken.put(user, newconf);
				
				Hashtable out = new Query("("+newconf.state+" :~ OutputParse, User:_), phrase(OutputParse, [Output], [])").oneSolution();
				return out.get("Output").toString().substring(1,out.get("Output").toString().length()-1);
			}
		}
		else {
			// Doorgaan met een bestaand gesprek			
			return gesprekken.get(user).step(input);
			
		}
		return null;
	}
	
	private class Conversation {
		public String state;
		public String user;
		// Een gesprek is een FSA
		// Een gebruiker doet met een input een stap uit de huidige staat naar de volgende staat en krijgt een output
		Conversation(String user) {
			user = this.user;
			state = "main";
		}
		
		public String step(String input) {
			String q = "step('"+user+"',"+state+",'"+input+"',Output,Nextstate)";
			Hashtable out = new Query(q).oneSolution();
			if (out == null) {
				System.err.println("Prolog: No solutions to "+ q);
				return null;
			}
			state = out.get("Nextstate").toString();
			return out.get("Output").toString().substring(1,out.get("Output").toString().length()-1);
		}
		
	}

}
