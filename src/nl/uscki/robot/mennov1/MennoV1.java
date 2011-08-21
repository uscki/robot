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
	
	
	public MennoV1() {
		init();
	}
	
	private void init() {
		commands = new HashMap<String, Command>();
		commands.put(Load.class.getSimpleName(), new Load());
		commands.put(Unload.class.getSimpleName(), new Unload());
		commands.put(Count.class.getSimpleName(), new Count());
		commands.put("Help", new Command() {
			
			@Override
			public String execute(String[] args) {
				StringBuilder aap = new StringBuilder();
				for(Command command : commands.values()) {
					aap.append(command.helpMsg());
				}
				
				return aap.toString();
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
	
	public String [] parseArguments(String readLine) {
		String [] args = readLine.split(" ");
		String [] output = null;
		if(commands.containsKey(args[0]))
		{
			output = new String[1];
			output[0] = commands.get(args[0]).execute(args);
			return output;
		}
		
		else {
			output = new String[listenerBots.size()];
			int i=0;
			for(Bot listener : listenerBots.values()){
				output[i++] = (listener.ask(readLine));
			}
			
			return output;
		}
	}
	
	/*public void run() {
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
	}*/
}