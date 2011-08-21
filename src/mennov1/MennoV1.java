package mennov1;

import java.util.HashMap;

import commands.Command;
import commands.Count;
import commands.Load;
import commands.Unload;

import bots.Bot;


/**
 * 
 * @author Jetze Baumfalk
 * @category Framework
 * 
 * This class is the core class of the Robot Framework.
 * It can execute commands and load/unload bots. Commands can be given to this class
 * by using either JabberClient or MennoV1Terminal.
 *
 */
public class MennoV1 {
	
	HashMap <String,Command> commands; // contains the commands (such like Help, Load, Time etc.)
	 // contains the list of currently loaded bots. Bots are either preloaded or can be loaded via the 'Load' command
	public HashMap<String, Bot> listenerBots;
	
	// for usage in other classes, cannot be accessed directly
	private static MennoV1 master;
	
	
	private MennoV1() {
		init();
	}
	
	// the Singleton pattern prevents that MennoV1 gets created multiple times.
	public static MennoV1 getInstance() {
		if(master == null)
			master = new MennoV1();
		return master;
	}
	
	/**
	 * Initializes the commands and the bots.
	 */
	private void init() {
		// load the commands
		commands = new HashMap<String, Command>();
		commands.put(Load.class.getSimpleName(), new Load());
		commands.put(Unload.class.getSimpleName(), new Unload());
		commands.put(Count.class.getSimpleName(), new Count());
		commands.put("Help", new Command() {
			
			@Override
			public String execute(String[] args) {
				StringBuilder aap = new StringBuilder();
				for(Command command : commands.values()) {
					aap.append(command.getClass().getSimpleName()+": "+command.helpMsg());
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

	
	/**
	 * Parses the input given by the user and return
	 * the output from either a command or all the bots currently running.
	 * @param readLine 	The input given by the user
	 * @return The output from either a command or all the bots currently running.
	 */
	public String [] parseArguments(String readLine) {
		
		String [] args = readLine.split(" "); 
		String [] output = new String[1];
		
		// is it a command?
		if(commands.containsKey(args[0]))
		{
			output[0] = commands.get(args[0]).execute(args);
			return output;
		}
		//Feed the input to all the bots
		else {
			boolean emptyOutput = true;
			if(listenerBots.size() > 0)
				output = new String[listenerBots.size()];
			int i=0;
			for(Bot listener : listenerBots.values()){
				output[i++] = listener.getClass().getSimpleName() +": "+(listener.ask(readLine));
				if(output[i-1] != "")
					emptyOutput = false;
			}
			// If all the bots didn't return output, create a nice message telling the user exactly that.
			if(emptyOutput)
				output[0] = "No output was returned by the bots for your input";
			
			return output;
		}
	}
}