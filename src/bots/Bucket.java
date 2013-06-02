package bots;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

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
				return "Als jij \"" + key + "\" zegt, zeg ik \"" + val + "\"!";
			}
			else return null;
		}
		else if (db.containsKey(in)) {
			return db.get(in);
		}
		else if (in.toLowerCase().equals("bucket remember")) {
			String mem = "BUCKETS LIST\n";
			Iterator it = db.entrySet().iterator();
		    while (it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        mem += "/" + pairs.getKey() + "/" + pairs.getValue() + "/\n";
		    }
		    try {
		    	MemoryLib.remember("bucket.txt", mem);
		    	return "I REMEMBER";
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	return "I FORGET";
		    }
		}
		else return null;
	}

}
