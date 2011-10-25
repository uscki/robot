package lib;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Log stuff to the web interface ("Menno's sewer")
 * 
 * @author vincent
 */
public class SewerSender {
	public enum Status {INFO, WARNING, ERROR}
	
	public static void logMessage(String message)
	{
		logMessage(message, Status.INFO);
	}
	
	/**
	 * Log messages to the sewer
	 * 
	 * @param message The message you want to log
	 * @param status Importance of the message (see the Status enum)
	 */
	public static void logMessage(String message, Status status)
	{
		String json = "{\"log\" : [ {\"";
		switch(status){
		case INFO:
			json += "info";
			break;
		case WARNING:
			json += "warning";
			break;
		case ERROR:
			json += "error";
			break;
		}
		json += "\" : \"" + message + "\"} ] }";
		
		sendJson(json);
	}
	
	/**
	 * Post een JSON-bericht naar de robotserver
	 * @param json
	 */
	protected static void sendJson(String json){
		String data;
		try {
			data = URLEncoder.encode("json", "UTF-8") + "=" + URLEncoder.encode(json, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// This would be really weird
			return;
		}
		
		URL u;
		try {
			u = new URL("https://studiereis.uscki.nl/log");
		} catch (MalformedURLException e) {
			// Fail silently
			return;
		}
		URLConnection conn;
		try {
			conn = u.openConnection();
		} catch (IOException e) {
			// Fail silently
			return;
		}
		conn.setDoOutput(true);
		OutputStreamWriter wr;
		try {
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(data);
			wr.flush();
		} catch (IOException e) {
			// Fail silently, invalid input
			return;
		}
	}
}
