package nl.uscki.robot.mennov1;

import bots.*;

public class Load extends Command {
	
	@Override
	public int execute(String [] args) {
		System.out.println("Woei, in load!");
		String s=args[1];
		
		Class cls = null;
		Bot obj = null;
		try {
			cls = Class.forName("bots."+s);
			obj= (Bot)cls.newInstance();
			MennoV1.master.listenerBots.put(obj.getClass().getSimpleName(),obj);
			System.out.println("Bot " + s+ " has been loaded");
		} catch (ClassNotFoundException e) {
			System.out.println("Invalid bot.");
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		 
		
		return 0;
	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return this.getClass().getSimpleName() + " %modulename%: loads a certain modules";
	}

}
