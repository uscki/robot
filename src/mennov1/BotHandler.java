package mennov1;

import java.util.ArrayList;
import java.util.HashMap;

import lib.SewerSender;
import bots.IBot;

import commands.Command;
import commands.Count;
import commands.Load;
import commands.Time;
import commands.Unload;

import events.Event;
import events.Response;
import events.TextEvent;


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
public class BotHandler {
	
	HashMap <String,Command> commands; // contains the commands (such like Help, Load, Time etc.)
	 // contains the list of currently loaded bots. Bots are either preloaded or can be loaded via the 'Load' command
	public HashMap<String,IBot> botList; //<interfacename,<botname,botobject>>
	
	// for usage in other classes, cannot be accessed directly
	private static BotHandler master;
	
	// the Singleton pattern prevents that MennoV1 gets created multiple times.
	public static BotHandler getInstance() {
		if(master == null)
			master = new BotHandler();
		return master;
	}
	
	/**
	 * Initializes the commands and the bots.
	 */
	private BotHandler() {
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
//					if(command.getClass().getSimpleName().length()>0)
//						aap.append(command.getClass().getSimpleName()+": ");
					aap.append(command.getClass().getSimpleName() + " " + command.helpMsg()+"\r\n");
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
		
		botList = new HashMap<String,IBot>();
	}
	
	/**
	 * Parses the input given by the user and return
	 * the output from either a command or all the bots currently running.
	 * @param readLine 	The input given by the user
	 * @return The output from either a command or all the bots currently running.
	 */
	public ArrayList<String>  parseArguments(String readLine, String user) {
		Event textEvent = new TextEvent(readLine,user);
		ArrayList <String> textResponseList = new ArrayList <String>();
		SewerSender.logMessage("Received message from " + user + ": " + readLine);
		
		// is it a command
		String [] args = readLine.split(" "); 
		String [] output = new String[1];
		
		String capitalized = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
		if(commands.containsKey(capitalized))
		{
			textResponseList.add(commands.get(capitalized).execute(args));
			return textResponseList;
		}
		
		//Feed the input to all the bots
		else {
			for(IBot bot : botList.values()) {
				Response response = bot.handleEvents(textEvent);
				handleResponse(response,textResponseList);
			}

			return textResponseList;
		}		
	}

	//TODO: iets met bomen ofzo, jatoch
	//(probleem: veel responses, wat moet dan de output zijn, bijv alleen de responses op de eerste respons?)
	public void handleResponse(Response res,ArrayList<String> textResponseList) {
		if(!res.response.equals("")){
			textResponseList.add(res.response);
			for(Event ev : res.getEvents()) {
				for(IBot bot : botList.values()) {
					Response res2 = bot.handleEvents(ev);
					handleResponse(res2,textResponseList);
				}
			}
		}
	}
}
