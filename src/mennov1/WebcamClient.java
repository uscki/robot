package mennov1;

import java.io.IOException;

import lib.SewerSender;

public class WebcamClient {
	public WebcamClient() {
		try {
			// Use python opencv bindings for now
			SewerSender.logMessage("Trying to start python script from Java");
			Runtime.getRuntime().exec("/home/mennov1/webcam/cam2web.py &");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
