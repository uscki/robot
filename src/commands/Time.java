package commands;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author Jetze Baumfalk
 *
 * Very simple command that shows the current time.
 */
public class Time extends Command {

	@Override
	public String execute(String[] args) {
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	    return "The current time on this machine is: "+sdf.format(cal.getTime());

	}

	@Override
	public String helpMsg() {
		// TODO Auto-generated method stub
		return "shows the current time on the machine that MennoV1 runs on.";
	}

}
