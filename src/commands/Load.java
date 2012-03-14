package commands;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;

import mennov1.BotHandler;
import bots.Bot;

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
		Bot obj = null;
		try {
			BotHandler bot = BotHandler.getInstance();
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
			obj = (Bot)cls.newInstance();
			String botName = obj.getClass().getSimpleName();

			for(Class clsInterface : obj.getClass().getInterfaces()) {
				if(clsInterface.getSimpleName().equals("Bot"))
					continue;
				String interfaceName = clsInterface.getSimpleName().substring(0,clsInterface.getSimpleName().length()-9);
				if(bot.subscriberList.containsKey(interfaceName))
					bot.subscriberList.get(interfaceName).put(botName,obj);
				else {
					HashMap <String,Bot> temp = new HashMap<String,Bot>();
					temp.put(botName,obj);
					bot.subscriberList.put(interfaceName,temp);
				}
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
