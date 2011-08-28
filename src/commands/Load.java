package commands;

import mennov1.MennoV1;
import bots.*;

/**
 * 
 * @author Jetze Baumfalk
 *
 * Used to load new bots into MennoV1.
 */
public class Load extends Command {
	
	@Override
	public String execute(String [] args) {
		
		String s = null;
		try
		{
			s=args[1];
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return "You need to supply the bot that needs to be loaded";
		}
		
		//O no, a very ugly hack to load random bots.
		Class cls = null;
		Bot obj = null;
		try {
			cls = Class.forName("bots."+s);
			obj = (Bot)cls.newInstance();
			MennoV1.getInstance().listenerBots.put(obj.getClass().getSimpleName(),obj);
			return ("Bot " + s+ " has been loaded");
		} catch (ClassNotFoundException e) { // The bot didn't exist
			return("Invalid bot.");
		} catch(NoClassDefFoundError e) {
			return("Invalid bot.");
		}
		catch (InstantiationException e) { // Someone tried to load 'Bot'
			return "Invalid bot.";
		} catch (IllegalAccessException e) { // Not sure yet.
			e.printStackTrace();
		}
		
		return("Invalid bot.");
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return " %modulename%: loads a certain modules";
	}

}
