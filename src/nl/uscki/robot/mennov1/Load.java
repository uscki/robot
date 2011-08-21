package nl.uscki.robot.mennov1;

import bots.*;

public class Load extends Command {
	
	@Override
	public String execute(String [] args) {
		String s=args[1];
		
		Class cls = null;
		Bot obj = null;
		try {
			cls = Class.forName("bots."+s);
			obj = (Bot)cls.newInstance();
			MennoV1.master.listenerBots.put(obj.getClass().getSimpleName(),obj);
			return ("Bot " + s+ " has been loaded");
		} catch (ClassNotFoundException e) {
			return("Invalid bot.");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return("Invalid bot.");
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName() + " %modulename%: loads a certain modules";
	}

}
