package bots;

import java.io.IOException;

public class Reboot extends AnswerBot {

	@Override
	public String ask(String in, String who) {
		if (in.equals("samsara")) {
			try {
					Runtime.getRuntime().exec("shutdown -r 5");
					return "From an inconstruable beginning comes transmigration. A beginning point is not evident, though beings hindered by ignorance and fettered by craving are transmigrating & wandering on. What do you think, monks: Which is greater, the tears you have shed while transmigrating & wandering this long, long time Ñ crying & weeping from being joined with what is displeasing, being separated from what is pleasing Ñ or the water in the four great oceans?";
			} catch(IOException e) {
				return e.toString();
			}
		} else {
			return null;
		}
	}

}
