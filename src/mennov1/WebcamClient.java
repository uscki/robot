package mennov1;

import java.io.IOException;

import bots.AnswerBot;

import lib.SewerSender;

public class WebcamClient extends AnswerBot {
	public WebcamClient() {
		try {
			// Use python opencv bindings for now
			System.out.println("Trying to start python script from java");
			SewerSender.logMessage("Trying to start python script from Java");
			Runtime.getRuntime().exec("/home/mennov1/webcam/cam2web.py &");
		} catch (IOException ex) {
			SewerSender.println(ex.toString());
		}
	}

	@Override
	public String ask(String in, String who) {
		if (in.toLowerCase().startsWith("killcam")) {
			try {
				Runtime.getRuntime().exec("killall cam2web.py");
				Runtime.getRuntime().exec("/home/mennov1/webcam/cam2web.py &");
				return "doet de webcam het nu?";
			} catch (IOException ex) {
				SewerSender.println(ex.toString());
				return "alles is kapot huilen huilen";
			}
		}
		return null;
	}
}
