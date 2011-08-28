package mennov1;

import java.util.HashMap;

import commands.Command;
import commands.Count;
import commands.Load;
import commands.Time;
import commands.Unload;

import bots.Bot;


/**
 * 
 * @author Jetze Baumfalk
 * @category Framework
 * 
 * This class is the core class of the Robot Framework.
 * It can execute commands and load/unload bots. Commands can be given to this class
 * by using any of the Menno V1-clients.
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
		commands.put(Time.class.getSimpleName(), new Time());
		commands.put("Help", new Command() {
			
			@Override
			public String execute(String[] args) {
				StringBuilder aap = new StringBuilder();
				for(Command command : commands.values()) {
					if(command.getClass().getSimpleName().length()>0)
						aap.append(command.getClass().getSimpleName()+": ");
					aap.append(command.helpMsg()+"\r\n");
				}
				
				return aap.toString();
			}

			@Override
			public String helpMsg() {
				// TODO Auto-generated method stub
				return "Help : displays this message";
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
	public String [] parseArguments(String readLine, String user) {
		
		String [] args = readLine.split(" "); 
		String [] output = new String[1];
		
		String capitalized = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
		// is it a command?
		if(commands.containsKey(capitalized))
		{
			output[0] = commands.get(capitalized).execute(args);
			return output;
		}
		//Feed the input to all the bots
		else {
			boolean emptyOutput = true;
			if(listenerBots.size() > 0)
				output = new String[listenerBots.size()];
			int i=0;
			for(Bot listener : listenerBots.values()){
				
				String response = listener.ask(readLine, user);
				if(null != response){
					output[i++] = listener.getClass().getSimpleName() +": "+response;
					if(output[i-1] != "")
						emptyOutput = false;
					
				}
			}
			// If all the bots didn't return output, create a nice message telling the user exactly that.
			//if(emptyOutput)
			//	output[0] = "No output was returned by the bots for your input";
			
			return output;
		}
	}
}