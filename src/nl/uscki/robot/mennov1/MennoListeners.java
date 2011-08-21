package nl.uscki.robot.mennov1;

import java.io.*;
import java.util.Hashtable;

import bots.*;

public class MennoListeners{
	
	public static void main(String[] args) {
		System.out.println("Welcome to MennoV1.");
		System.out.println("Type help for a list of commands");
		System.out.println("Type exit to quit everything!");
		
		Hashtable<String, Bot> listenerBots = new Hashtable<String, Bot>();
		
		BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
		listenerBots.put("Echobot", new Echobot());
		
		while(true) {
			String input = null;
			try {
				input = buffy.readLine();
			} catch (IOException e) {
				System.out.println("O noes, an error occured!");
				e.printStackTrace();
				System.exit(1);
			}
			
			if(input.startsWith("exit")){
				// Wha-ever dude
			} else if(input.startsWith("count")) {
				System.out.println("Er zijn op dit moment " + listenerBots.size() + " scripts geladen.");
			}
			
			for(Bot listener : listenerBots.values()){
				System.out.println(listener.ask(input));
			}
		}
	}
	

}
