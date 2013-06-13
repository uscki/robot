package library;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UsckiSite {

	private static String login = "";
	private static String password = "";
	private static String url = "https://www.uscki.nl/~kruit/zebra/";

	public static Document request(String get) {
		
		login = Settings.getInstance().get("uscki-login");
		password = Settings.getInstance().get("uscki-password");
		
		
		HttpURLConnection connection = null;
		URL serverAddress = null;
		String charset = "UTF-8";	      

		try {
			serverAddress = new URL(url + "?api&" + get);
			//set up out communications stuff
			connection = null;

			//Set up the initial connection
			connection = (HttpURLConnection)serverAddress.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.setReadTimeout(10000);
			connection.setRequestProperty("Accept-Charset", charset);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=" + charset);

			connection.connect();

			OutputStream output = null;
			String post = String.format("login=%s&password=%s", 
					URLEncoder.encode(login, charset), 
					URLEncoder.encode(password, charset));
			try {
				output = connection.getOutputStream();
				output.write(post.getBytes(charset));
			} finally {
				if (output != null) try { output.close(); } catch (IOException logOrIgnore) {}
			}

			//read the result from the server and parse xml
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(connection.getInputStream());
			doc.getDocumentElement().normalize();
			
			return doc;

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		finally
		{
			//close the connection, set all objects to null
			connection.disconnect();
			connection = null;
		}

		return null;
	}
}
