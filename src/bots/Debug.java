package bots;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;

import lib.SewerSender;
import mennov1.EventBus;
import mennov1.IrcClient;
import events.SendChatEvent;

// Remember: runtime exec is not a command line.
// It cannot perform pipes and shit

public class Debug extends AnswerBot {

	@Override
	public String ask(String in, String who) {
		if (in.equals("samsara")) { // reboot
			try {
					Runtime.getRuntime().exec("shutdown -r 1");
					EventBus.getInstance().event(new SendChatEvent(this, IrcClient.getInstance(), "#incognito", "From an inconstruable beginning comes transmigration. A beginning point is not evident, though beings hindered by ignorance and fettered by craving are transmigrating & wandering on. What do you think, monks: Which is greater, the tears you have shed while transmigrating & wandering this long, long time - crying & weeping from being joined with what is displeasing, being separated from what is pleasing - or the water in the four great oceans?"));
					return "/quit" ;
			} catch(IOException ex) {
				return ex.toString();
			}
		} else if (in.equals("biecht")) { // poop log to server
			try {
				SewerSender.logMessage(readFile("/home/mennov1/start-menno.log"));
				return "Ego te absolvo a peccatis tuis in nomine Patris et Filii et Spiritus Sancti. Amen.";
			} catch(IOException ex) {
				return ex.toString();
			}
		} else if (in.equals("nabiyyun")) { // running processes
			try {
				Process process = Runtime.getRuntime().exec(new String[] { "ps", "cax" });
				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				String lines = "";
				String read="";
				while ((read = reader.readLine()) != null) {
					lines += read + "\n";
				}
				SewerSender.logMessage(lines);
				process.destroy();
				return "ashadu 'al-la ilaha illa-llahu wa 'ashadu 'anna muhammadan rasulu-llah.";
			} catch(IOException ex) {
				return ex.toString();
			}
		} else {
			return null;
		}
	}
	
	private static String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}
	
}
