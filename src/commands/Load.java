package commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import mennov1.BotHandler;
import bots.IBot;

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
		if(args.length <= 1)
			return "You need to supply the bot that needs to be loaded";
		s=args[1];
		
		
		//O no, a very ugly hack to load random bots.
		Class cls = null;
		IBot obj = null;
		try {
			BotHandler botHandler = BotHandler.getInstance();
			if(args.length == 2)
				cls = Class.forName("bots."+s);
			else { //dynamisch iets laden
				// bron: http://www.exampledepot.com/egs/java.lang/LoadClass.html)
				// werkt zowel voor absolute als relatieve paths
				// TODO: checken voor unix-based systemen.
				File file = new File(args[2]); 
				URL url = file.toURI().toURL(); 
			    URL[] urls = new URL[]{url};
			    ClassLoader cl = new URLClassLoader(urls);
			    cls = cl.loadClass(s);
			}
			obj = (IBot)cls.newInstance();
			String botName = obj.getClass().getSimpleName();

			if(!botHandler.botList.containsKey(botName)){
				botHandler.botList.put(botName,obj);
			}	

			return ("Bot " + s+ " has been loaded");
		} catch (ClassNotFoundException e) { // The bot didn't exist
			return("Invalid bot.");
		} catch(NoClassDefFoundError e) {	// Error while instantiatin' class.
			return("Invalid bot.");
		}
		catch (InstantiationException e) { // Someone tried to load 'Bot'
			return "Invalid bot.";
		} catch (IllegalAccessException e) { // Not sure yet.
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return("Invalid bot.");
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return " %modulename%: loads a certain modules\t [%location%]: the location of the module (.class file)";
	}

}
