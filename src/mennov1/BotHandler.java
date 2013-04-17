package mennov1;

import java.util.EventObject;
import java.util.HashMap;

import mennov1.commands.Clients;
import mennov1.commands.Command;
import mennov1.commands.Count;
import mennov1.commands.List;
import mennov1.commands.Load;
import mennov1.commands.Time;
import mennov1.commands.Unload;

import events.ReceiveChatEvent;
import events.SendChatEvent;


/**
 * 
 * @author Jetze Baumfalk, Benno Kruit
 * @category Framework
 * 
 * This class is the core class of the Robot Framework.
 * It can execute commands and load/unload bots. Commands can be given to this class
 * by using any of the Menno V1-clients.
 *
 */
public class BotHandler implements Listener<ReceiveChatEvent>{

	HashMap <String,Command> commands; // contains the commands (such like Help, Load, Time etc.)
	// contains the list of currently loaded bots. Bots are either preloaded or can be loaded via the 'Load' command

	// for usage in other classes, cannot be accessed directly
	private static BotHandler master;

	// the Singleton pattern prevents that the bothandler gets created multiple times.
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
		commands.put(List.class.getSimpleName(), new List());
		commands.put(Clients.class.getSimpleName(), new Clients());
		commands.put("Help", new Command() {
			@Override
			public String execute(String[] args) {
				StringBuilder aap = new StringBuilder();
				for(Command command : commands.values()) {
					aap.append(command.getClass().getSimpleName() + " " + command.helpMsg()+"\r\n");
				}
				return aap.toString();
			}

			@Override
			public String helpMsg() {
				return "Help : displays this message";
			}
		});
	}

	public void event(ReceiveChatEvent e) {
		// is it a command
		String [] args = e.message.split(" "); 
		
		if (args[0].length() > 0) {
			String capitalized = args[0].substring(0, 1).toUpperCase() + args[0].substring(1).toLowerCase();
			if(commands.containsKey(capitalized))
			{
				EventBus.getInstance().event(new SendChatEvent(master, e.client, e.sender, commands.get(capitalized).execute(args)));
			}
		}
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof ReceiveChatEvent);
	}
}
