package library;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Settings {
	
	static Settings master;
	
	private Map<String, String> values;
	
	public static Settings getInstance() {
		if(master == null)
			master = new Settings();
		return master;
	}
	
	Settings() {
		values = new HashMap<String, String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader("settings.txt"));
			try
			{
				String line;
				while ((line= in.readLine()) != null) {
					int split = line.indexOf("=");
					values.put(line.substring(0, split), line.substring(split+1));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally
			{
				in.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	
	public String get(String key) {
		return values.get(key);
	}
}
