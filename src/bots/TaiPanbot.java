package bots;

import lib.LaTeXOutput;
import java.io.IOException;

public class TaiPanbot implements Bot {
	
	boolean started;
	String[] teams; //0&1 zijn n team, net als 2&3
	int score_team1;
	int score_team2;
	LaTeXOutput l;

	public String ask(String input, String user){
		
		//Overzicht
		if(input.contains("commandos")){
			return "taipan spelen, teams{4 namen}, update{2 scores}, score";
			
		}
		
		//Start n potje TaiPan
		if (!started && input.contains("taipan") && input.contains("spelen")) {
			
			return "Wie doen er mee? Format: teams p1 p2 p3 p4, waarbij p1 en p2 een team is.";
			
		}
		
		//Teams {4 namen}
		if (!started && input.contains("teams")) {
			
			String[] names = input.split("\\s");
			
			if(names.length == 5) {
				teams = new String[4];
				for(int i = 0; i < 4; i++){
					teams[i] = names[i+1];
				}
				score_team1 = 0;
				score_team2 = 0;
				started = true;
				try {
					
					//Dit moet natuurlijk anders!
					String path = "C:\\Users\\Sjoerd\\Desktop\\";
					
					l = new LaTeXOutput(path,"taipanbot");
					l.documentClass("article");
					l.usePackage("table","xcolor");
					l.beginEnvironment("document");
					l.beginEnvironment("center");
					l.writeToTeX("\\rowcolors{1}{blue!20!lightgray}{lightgray!20!white}\n");
					l.tabular("|l|c|c|");
					l.writeToTeX("\\hline\n");
					l.writeToTeX("Teams: &" + teams[0] + " en "+teams[1]+"&"+teams[2] + " en "+teams[3]+" \\\\\n");
					l.writeToTeX("\\hline\n");
					
				} catch(IOException e){
					System.err.println("Caught IOException: "
	                        + e.getMessage());
					return null;
				}
				return "Veel plezier!";
			}
			return "Probeer opnieuw";
			
		}
		
		//Update de scores {score1, score2}
		if(started && input.contains("update")) {		
			String[] scores = input.split("\\s");
			if(scores.length == 3){
				
				int s1,s2;
				try{
					s1 = Integer.parseInt(scores[1]);
					s2 = Integer.parseInt(scores[2]);
					score_team1 += s1;
					score_team2 += s2;
					l.writeToTeX("&"+s1+"&"+s2+" \\\\\n");
				} catch(NumberFormatException e) {
					return "gebruik: update scoreTeam1 scoreTeam2";
				}  catch(IOException e){
					System.err.println("Caught IOException: "
	                        + e.getMessage());
					return null;
				}
			}
			return "Scores bijgewerkt.";
		}
		
		//Genereer de scores in LaTeX
		if (started && input.contains("score")) {
			try{
				l.writeToTeX("\\hline\n");
				l.writeToTeX("Totaal:&"+score_team1 +"&"+score_team2+"\\\\\n");
				l.writeToTeX("\\hline\n");
				l.endEnvironment("tabular");
		        l.endEnvironment("center");
		        l.endEnvironment("document");
		        l.closeFileWriter();  
			}  catch(IOException e){
				System.err.println("Caught IOException: "
                        + e.getMessage());
				return null;
			}
			l.pdfTeX();
			//vervang showTeX() door postToWeb van de pdf en n link?
	        l.showTeX();
	        started = false;
			return("*Link naar de scores*");
		}
		return null;
	}
	
}
