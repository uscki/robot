package bots;

import java.io.IOException;
import java.util.Hashtable;

import library.LaTeXOutput;

public class TaiPanbot  extends AnswerBot {
	
	protected Hashtable<String, Boolean> started;
	protected Hashtable<String, String[]> teams; //0&1 zijn n team, net als 2&3
	protected Hashtable<String, Integer> score_team1;
	protected Hashtable<String, Integer> score_team2;
	LaTeXOutput l;

	public String ask(String input, String user){
		
		//Overzicht
		if(input.contains("commandos")){
			return "taipan spelen, teams{4 namen}, update{2 scores}, score";
		}
		
		//Start n potje TaiPan
		if (started.get(user) != true && input.contains("taipan") && input.contains("spelen")) {
			
			return "Wie doen er mee? Format: teams p1 p2 p3 p4, waarbij p1 en p2 een team is.";
			
		}
		
		//Teams {4 namen}
		if (started.get(user) != true && input.contains("teams")) {
			
			String[] names = input.split("\\s");
			
			if(names.length == 5) {
				String[] userTeams = new String[4];
				for(int i = 0; i < 4; i++){
					userTeams[i] = names[i+1];
				}
				teams.put(user, userTeams);
				score_team1.put(user, 0);
				score_team2.put(user, 0);
				started.put(user, true);
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
					l.writeToTeX("Teams: &" + teams.get(user)[0] + " en "+teams.get(user)[1]+"&"+teams.get(user)[2] + " en "+teams.get(user)[3]+" \\\\\n");
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
		if(started.get(user) == true && input.contains("update")) {		
			String[] scores = input.split("\\s");
			if(scores.length == 3){
				
				int s1,s2;
				try{
					s1 = Integer.parseInt(scores[1]);
					s2 = Integer.parseInt(scores[2]);
					if((score_team1.get(user) + score_team2.get(user)) % 100 != 0){
						return "Tel opnieuw!";
					}
					score_team1.put(user, score_team1.get(user) + s1);
					score_team2.put(user, score_team2.get(user) + s2);
					l.writeToTeX("&"+s1+"&"+s2+" \\\\\n");
				} catch(NumberFormatException e) {
					return "gebruik: update scoreTeam1 scoreTeam2";
				}  catch(IOException e){
					System.err.println("Caught IOException: "
	                        + e.getMessage());
					return null;
				}
			}
			if(score_team1.get(user) > 999 && score_team2.get(user) > 999){
				String s = "Gelijkspel: "+ stand(user) + "!";
				return s;
			}
			if(score_team1.get(user) > 999){
				String s = "Team 1 wint met "+ stand(user); 
				return s;
			}
			if(score_team2.get(user) > 999){
				String s = "Team 2 wint met "+ stand(user); 
				return s;
			}
			return "Scores bijgewerkt.";
		}
		
		//Tussenstand
		if (started.get(user) == true && input.contains("tussenstand")){
			return "De stand is" + stand(user);
		}
		
		//Genereer de scores in LaTeX
		if (started.get(user) == true && input.contains("score")) {
			try{
				l.writeToTeX("\\hline\n");
				l.writeToTeX("Totaal:&"+score_team1.get(user) +"&"+score_team2.get(user)+"\\\\\n");
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
	        started.put(user, false);
			return("*Link naar de scores*");
		}
		return null;
	}
	
	private String stand(String user){
		String stand = " "+score_team1.get(user)+" v.s. "+score_team2.get(user);
		return stand;
	}
	
}
