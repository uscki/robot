package nl.uscki.robot.mennov1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

import bots.Bot;

public class MennoV1 {
	
	HashMap <String,Command> commands;
	HashMap<String, Bot> listenerBots;
	static MennoV1 master;
	private void init() {
		commands = new HashMap<String, Command>();
		commands.put(Load.class.getSimpleName(), new Load());
		commands.put(Unload.class.getSimpleName(), new Unload());
		commands.put(Count.class.getSimpleName(), new Count());
		commands.put("help", new Command() {
			
			@Override
			public int execute(String[] args) {
				for(Command command : commands.values()) {
					System.out.println(command.helpMsg());
				}
				
				return 0;
			}

			@Override
			public String helpMsg() {
				// TODO Auto-generated method stub
				return "help : displays this message";
			}
		}
		);
		
		listenerBots = new HashMap<String, Bot>();
	}
	
	private void welcomeMessage() {
		System.out.println("Welcome to MennoV1.");
		System.out.println("Type help for a list of commands");
		System.out.println("Type exit to quit everything!");
		
	}
	
	public void run() {
		init();
		welcomeMessage();
		
		BufferedReader buffy = new BufferedReader(new InputStreamReader(System.in));
		
		//hier de loop
		while(true) {
			try {
				String readLine = buffy.readLine();
				String [] args = readLine.split(" ");
				
				if(commands.containsKey(args[0]) )
					commands.get(args[0]).execute(args);
				else {
					for(Bot listener : listenerBots.values()){
						System.out.println(listener.ask(readLine));
					}
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
	
	public static void main(String [] args) {
		master = new MennoV1();
		System.out.println("Starting up the bot");
		master.run();
	}
}