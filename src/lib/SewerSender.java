package lib;

import java.io.*;
import java.net.URL;
import java.net.URLEncoder;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * Log stuff to the web interface ("Menno's sewer")
 * 
 * @author vincent
 */
public class SewerSender {
	public enum Status {INFO, WARNING, ERROR}
	
	static TrustManager trm;
	
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
		
    	ranzigeSSLCertHack();
		
		try {
			data = "json=" + URLEncoder.encode(json, "UTF-8");
			URL u = new URL("https://robot.uscki.nl/log/log.php");
			
			// Hier gebruiken we als enig verschil dus HttpsURLConnection ipv URLConnection
			HttpsURLConnection connection = (HttpsURLConnection) u.openConnection();
			
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", 
		    		  "application/x-www-form-urlencoded");
					
			connection.setRequestProperty("Content-Length", "" + 
				   Integer.toString(data.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");  
					
			connection.setUseCaches (false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			//Send request
			DataOutputStream wr = new DataOutputStream (
					connection.getOutputStream ());
			wr.writeBytes (data);
			wr.flush ();
			wr.close ();

			//Get Response	
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer(); 
			while((line = rd.readLine()) != null) {
		    	  response.append(line);
		    	  response.append('\r');
			}
			rd.close();

			System.out.println(response.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
    //Zeer ranzige hack omdat de SSL in dit geval overbodig is
	private static void ranzigeSSLCertHack() {
		TrustManager[] trustAllCerts = new TrustManager[]{
			  new X509TrustManager() {

				public java.security.cert.X509Certificate[] getAcceptedIssuers()
				{
				    return null;
				}
				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
				{
				    //No need to implement.
				}
				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
				{
				    //No need to implement.
				}
			  }
		};

		// Install the all-trusting trust manager
		try 
		{
		    SSLContext sc = SSLContext.getInstance("SSL");
		    sc.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} 
		catch (Exception e) 
		{
		    System.out.println(e);
		}

		HostnameVerifier hv = new HostnameVerifier() {
			public boolean verify(String urlHostName, SSLSession session) {
				System.out.println("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
				return true;
			}
		};

		HttpsURLConnection.setDefaultHostnameVerifier(hv);
	}
}
