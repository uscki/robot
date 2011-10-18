/* Author: Rick Sen
   With many thanks to Vincent Tunru for his example program Hogerlager!
   Free to use and abuse
 */

package bots;

import java.util.Hashtable;
import java.util.Random;

public class BoterKaasEnEieren implements Bot {
	public enum Cel {
		X, O, EMPTY
	}
	
	Random random;
	
	// For multiple  simultanious game instances
	protected Hashtable<String, Cel[][]> velden;
	
	
	// Constructor
	public BoterKaasEnEieren() {
		velden = new Hashtable<String, Cel[][]>();
		random = new Random(System.currentTimeMillis());
	}
	
	public String ask(String input, String user){
		if((input.contains("boter") | input.contains("kaas") | input.contains("eieren") | input.contains("kruisje") | input.contains("nulletje")) & input.contains("spelen")){
			velden.put(user, newVeld());
			printVeld(velden.get(user));
			return "Goed, we spelen boter, kaas en eieren. Jij mag beginnen " + user + "!";
			
		} else if(input.startsWith("Zet ") || input.startsWith("zet ") || input.startsWith("zet: ") || input.startsWith("Zet: ") || input.startsWith("Doe: ") || input.startsWith("Doe ") || input.startsWith("doe: ") || input.startsWith("doe ")) {
			// Alleen spelen als de speler een spelletje is begonnen
			if(!velden.containsKey(user)){
				return null;
			}
			
			int kolom;
			int rij;
			
			// de rij
			try{
				rij = Integer.parseInt(input.substring(5, input.length()));
			} catch(NumberFormatException e) {
				try{
					rij = Integer.parseInt(input.substring(6, input.length()));
				} catch(NumberFormatException f) {
					return null;
				}
			}
			rij--;
			
			// de kolom
			if(input.substring(4,input.length()).contains("a") | input.substring(4,input.length()).contains("A")) {
				kolom = 0;
				System.out.println("a");
			} else if(input.substring(4,input.length()).contains("b") | input.substring(4,input.length()).contains("B")) {
				kolom = 1;
			} else if(input.substring(4,input.length()).contains("c") | input.substring(4,input.length()).contains("C")) {
				kolom = 2;
			} else {
				kolom = 3; // Geeft error; is om te zorgen dat er geen "kolom can be unassigned" meldingen komen
			}
			
			// doe de zet
			doeZetUser(rij, kolom, user);
			System.out.println(user + " heeft een zet gedaan:");
			printVeld(velden.get(user));
			
			// ga na of speler heeft gewonnen
			if(checkVeld(user).equals(Cel.X)) {
				return "Je hebt gewonnen! Gefeliciteerd " + user + "!";
			}
			
			// kijk of het bord vol is
			Cel[][] veld = velden.get(user);
			int count = 0;
			for(int x = 0; x < 3; x++) {
				for(int y = 0; y < 3; y++) {
					if(veld[x][y].equals(Cel.EMPTY)){
						count++;
					}
				}
				if(count == 0) {
					return "Het veld is vol! Gelijkspel! Nog een potje?";
				}
			}
			
			// bereken en doe tegenzet
			randomZet(user);
			System.out.println("ik heb een zet gedaan:");
			printVeld(velden.get(user));
			if(checkVeld(user).equals(Cel.O)) {
				return "Ik hebt gewonnen! Wil je nog een potje spelen?";
			}
			return "jij bent";
			
		}
		return "";
	}
	
	public Cel checkVeld(String user) {
		Cel[][] veld = velden.get(user);
		if(veld[0][0].equals(Cel.X) && veld[0][1].equals(Cel.X) && veld[0][2].equals(Cel.X)) {return Cel.X;}
		if(veld[1][0].equals(Cel.X) && veld[1][1].equals(Cel.X) && veld[1][2].equals(Cel.X)) {return Cel.X;}
		if(veld[2][0].equals(Cel.X) && veld[2][1].equals(Cel.X) && veld[2][2].equals(Cel.X)) {return Cel.X;}
		
		if(veld[0][0].equals(Cel.X) && veld[1][0].equals(Cel.X) && veld[2][0].equals(Cel.X)) {return Cel.X;}
		if(veld[0][1].equals(Cel.X) && veld[1][1].equals(Cel.X) && veld[2][1].equals(Cel.X)) {return Cel.X;}
		if(veld[0][2].equals(Cel.X) && veld[1][2].equals(Cel.X) && veld[2][2].equals(Cel.X)) {return Cel.X;}
		
		if(veld[0][0].equals(Cel.X) && veld[1][1].equals(Cel.X) && veld[2][2].equals(Cel.X)) {return Cel.X;}
		if(veld[2][2].equals(Cel.X) && veld[1][1].equals(Cel.X) && veld[0][0].equals(Cel.X)) {return Cel.X;}
		
		if(veld[0][0].equals(Cel.O) && veld[0][1].equals(Cel.O) && veld[0][2].equals(Cel.O)) {return Cel.O;}
		if(veld[1][0].equals(Cel.O) && veld[1][1].equals(Cel.O) && veld[1][2].equals(Cel.O)) {return Cel.O;}
		if(veld[2][0].equals(Cel.O) && veld[2][1].equals(Cel.O) && veld[2][2].equals(Cel.O)) {return Cel.O;}
		
		if(veld[0][0].equals(Cel.O) && veld[1][0].equals(Cel.O) && veld[2][0].equals(Cel.O)) {return Cel.O;}
		if(veld[0][1].equals(Cel.O) && veld[1][1].equals(Cel.O) && veld[2][1].equals(Cel.O)) {return Cel.O;}
		if(veld[0][2].equals(Cel.O) && veld[1][2].equals(Cel.O) && veld[2][2].equals(Cel.O)) {return Cel.O;}
		
		if(veld[0][0].equals(Cel.O) && veld[1][1].equals(Cel.O) && veld[2][2].equals(Cel.O)) {return Cel.O;}
		if(veld[2][2].equals(Cel.O) && veld[1][1].equals(Cel.O) && veld[0][0].equals(Cel.O)) {return Cel.O;}
		
		return Cel.EMPTY;
	}
	
	private void randomZet(String user) {
		int rij = random.nextInt(3);
		int kolom = random.nextInt(3);
		Cel[][] veld = velden.get(user);
		Cel temp = veld[rij][kolom];
		switch(temp) {
			case X: randomZet(user); break;
			case O: randomZet(user); break;
			case EMPTY: veld[rij][kolom] = Cel.O; velden.put(user,veld); break;
		}
	}

	private void doeZetUser(int rij, int kolom, String user) {
		Cel[][] veld = velden.get(user);
		
		veld[rij][kolom] = Cel.X;
		
		velden.put(user,veld);
	}
	
	public void printVeld(Cel[][] veld) {
		System.out.print("    a   b   c\n");
		System.out.print("  -------------\n");
		for(int x = 0; x < 3; x++){
			System.out.print(x+1 + " | ");
			for(int y = 0; y < 3; y++){
				System.out.print(toString(veld[x][y]) + " | ");
			}
			System.out.print("\n  -------------\n");
		}
	}
	
	private String toString(Cel z){
		switch(z){
			case X: return "X";
			case O: return "O";
			case EMPTY: return " ";
		}
		return "?";
	}
	
	public Cel[][] newVeld() {
		Cel[][] veld2 = {{Cel.EMPTY, Cel.EMPTY, Cel.EMPTY}, {Cel.EMPTY, Cel.EMPTY, Cel.EMPTY}, {Cel.EMPTY, Cel.EMPTY, Cel.EMPTY}};
		return veld2;
	}
	
}
