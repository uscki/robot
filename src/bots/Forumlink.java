package bots;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Forumlink implements Bot {

	@Override
	public String ask(String input, String user) {
		if(!input.contains("https://www.uscki.nl/?pagina=Forum/ViewTopic&topicId=")){
			return null;
		}
		int startOfLink = input.indexOf("https://www.uscki.nl/?pagina=Forum/ViewTopic&topicId=");
		int endOfLink = input.indexOf(' ', startOfLink);
		if(endOfLink < 0){
			endOfLink = input.length();
		}
		String url = input.substring(startOfLink, endOfLink);
		
		URL urlObject;
		try {
			urlObject = new URL(url);
		} catch (MalformedURLException e) {
			// Invalid URL
			return null;
		}
		
		InputStream in;
		try {
			in = urlObject.openStream();
		} catch (IOException e) {
			// Couldn't fetch URL
			return null;
		}
		
		BufferedInputStream bin = new BufferedInputStream(in);
		
		String data = "";
		
		try {
			for(int read = bin.read(); read != -1; read = bin.read()){
				data += (char) read;
			}
		} catch (IOException e) {
			// Invalid webpage contents
			return null;
		}
		
		if(!data.contains("<title>") || !data.contains(" - USCKI Incognito</title>")){
			return null;
		}
		
		String title = data.substring(data.indexOf("<title>") + 7, data.indexOf(" - USCKI Incognito</title>"));
		
		return title;
	}

}
