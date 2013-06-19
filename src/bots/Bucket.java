package bots;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import library.MemoryLib;

/**
 * Leer Menno dingen zeggen!
 * 
 * @author Benno
 */

public class Bucket extends AnswerBot {
	
	private Hashtable<String, String> db;
	
	public Bucket() {
		db = new Hashtable<String, String>();
		// Lees antwoorden naar de hashtable
		try {
			for (String l : MemoryLib.readMemory("bucket.txt").split("\n")) {
				String[] kv = l.split("/");
				if (kv.length > 2) {
					db.put(kv[1], kv[2]);
				}
			}
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	// Werkt nu met reguliere expressies.
	// Voorbeeld: als ik "hoi ik ben (\w+)" zeg dan zeg jij "hoi \1"
	@Override
	public String ask(String in, String who) {
		if(in.toLowerCase().startsWith("als ik ")) {
			// Zoek dingen tussen aanhalingstekens
			int[] bounds = {0, 0, 0, 0};
			int i=0;
			for (int j = 0; (j < in.length() && i<4); j++) {
				if(in.charAt(j) == '\"') {
					bounds[i] = j;
					i++;
				}
			}
			
			if (i==4) {
				String key = in.substring(bounds[0]+1, bounds[1]);
				String val = in.substring(bounds[2]+1, bounds[3]);
				db.put(key, val);
				// TODO: Bucket moet natuurlijk helemaal niet altijd alles opnieuw onthouden, das lelijk!
				bucket_remember();
				return "Als jij \"" + key + "\" zegt, zeg ik \"" + val + "\"!";
			}
			else return null;
		}
		else {
			// Check voor elke entry of er een match is op de input.
			// Zo ja, geef de input dan aangepast terug.
			for (Map.Entry e : db.entrySet()) {
				Pattern p = Pattern.compile((String)e.getKey(), Pattern.CASE_INSENSITIVE);
				Matcher m = p.matcher(in);
				if (m.find()){
					return stringmanipulatie((String)e.getValue(), m);
				}
			}
		}
		return null;
	}

	// vervang elke \\\d+ (dus bvb \1) door die match in m.group()
	public String stringmanipulatie(String value, Matcher m) {

		StringBuffer sb = new StringBuffer();
		Pattern p = Pattern.compile("\\\\(\\d+)", Pattern.CASE_INSENSITIVE);
		Matcher m2 = p.matcher(value);
		int index = 0;
		while(!m2.hitEnd()) {
			if(m2.find()) {
				for (int i = index; i < m2.start(); i++) {
					sb.append(value.charAt(i));
				}
				index = m2.end();
				sb.append(m.group(Integer.parseInt(m2.group(1))));
			}
		}
		for (int i = index; i < value.length(); i++) {
					sb.append(value.charAt(i));
				}

		return sb.toString();

	}

	public void bucket_remember(){
		String mem = "BUCKETS LIST\n";
		Iterator it = db.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        mem += "/" + pairs.getKey() + "/" + pairs.getValue() + "/\n";
	    }
	    try {
	    	MemoryLib.remember("bucket.txt", mem);
	    	//return "I REMEMBER";
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	//return "I FORGET";
	    }
	}

}
