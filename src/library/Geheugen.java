package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.Serializable;
import java.lang.ClassNotFoundException;


/**
 * Long-term memory
 * This is for stuff we don't want a module for on uscki.nl, so chats logs and stuff
 * 
 * @author Benno
 */

public class Geheugen {

	static String dir = "resources/";

	public static void onthoud(String fname, Serializable obj) throws IOException {
		if (fname != "") {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(dir + fname));
			oos.writeObject(obj);
			oos.flush();
			oos.close();
		}
	}

	public static Object herinner(String fname) throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(dir + fname));
		Object obj = ois.readObject();
		ois.close();
		return obj;
	}
}
