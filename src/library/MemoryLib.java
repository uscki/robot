package library;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Long-term memory
 * This is for stuff we don't want a module for on uscki.nl, so chats logs and stuff
 * 
 * @author Benno
 */

public class MemoryLib {

	static String dir = "dir/";

	public static void remember(String fname, String contents) throws IOException {
		if (fname != "") {
			BufferedWriter out = new BufferedWriter(new FileWriter(dir + fname));
			out.write(contents);
			out.close();
		}
	}

	public static String readMemory(String fname) throws IOException {
		String out = "";
		final BufferedReader in = new BufferedReader(new FileReader(dir + fname));
		try
		{
			String line;
			while ((line= in.readLine()) != null)
				out += line + "\n";
		}
		finally
		{
			in.close();
		}
		return out;
	}
}
