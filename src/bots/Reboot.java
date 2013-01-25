package bots;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import lib.SewerSender;
import mennov1.EventBus;
import mennov1.IrcClient;
import events.SendChatEvent;

// Remember: runtime exec is not a command line.
// It cannot perform pipes and shit

public class Reboot extends AnswerBot {

	@Override
	public String ask(String in, String who) {
		if (in.equals("samsara")) {
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
