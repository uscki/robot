package mennov1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bots.Bot;

/**
 * 
 * @author Jetze Baumfalk, Vincent Tunru
 * @category Framework
 * 
 * A basic terminal that can be used to communicate with MennoV1.
 */
public class TerminalClient implements Runnable{
	

	public TerminalClient() {
	}
	
	public static void main(String [] args) {
		TerminalClient client = new TerminalClient();
		client.runClient();
	}
	
	private void welcomeMessage() {
		System.out.println("Welcome to MennoV1.");
		System.out.println("Type help for a list of commands");
		System.out.println("Type exit to quit everything!");
		
	}
	
	public void runClient() {
		welcomeMessage();
		while(true) {
			try {
				BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
				String readLine = buffy.readLine();
				String [] outputs = BotHandler.getInstance().parseArguments(readLine, System.getProperty("user.name"));
				for(String s : outputs) {
					if(null == s){
						continue;
					}
					System.out.println(s);
				}
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

	@Override
	public void run() {
		// TODO Auto-generated method stub
		runClient();
	}
}
