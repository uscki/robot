package nl.uscki.robot.mennov1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class MennoV1 {
	
	
	public static void main(String [] args) {
		System.out.println("Welcome to MennoV1.");
		System.out.println("Type help for a list of commands");
		System.out.println("Type exit to quit everything!");
		
		BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
		
		//hier de loop
		while(true) {
			try {
				
				buffy.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("O noes, an error occured!");
				e.printStackTrace();
			}
			finally {
				System.out.println("Cleaning up the mess.");
			}
		}
		
		
	}
}