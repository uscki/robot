package nl.uscki.robot.mennov1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bots.Bot;

public class MennoV1Terminal {
	
	MennoV1 master;

	MennoV1Terminal() {
		if(MennoV1.master == null)
			MennoV1.master = new MennoV1();
		master = MennoV1.master;
		run();
	}
	
	public static void main(String [] args) {
		MennoV1Terminal temp = new MennoV1Terminal();
	}
	
	private void welcomeMessage() {
		System.out.println("Welcome to MennoV1.");
		System.out.println("Type help for a list of commands");
		System.out.println("Type exit to quit everything!");
		
	}
	
	public void run() {
		welcomeMessage();
		while(true) {
			try {
				BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
				String readLine = buffy.readLine();
				master.parseArguments(readLine);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("O noes, an error occured!");
				e.printStackTrace();
			}
			finally {
				//System.out.println("In the end, it doesn't even matter");
			}
		}
	}
}
