package bots;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import events.Event;
import events.FapEvent;
import events.Response;
import events.TextEvent;

public class FapSwapAppBot implements IBot<Event> {

	@Override
	public void event(Event event) {
		if(event instanceof TextEvent){
			Response response = new Response();
			if(startsWithLowerCase(event.info,"fapswap "))
			{
				
				if(event.info.length() <= 8)
				{
					response.setResponse("Jong, you didn't add a faplink! Share a little.");
					return response;
				} 
				else if(!validFapLink(event.info.substring(8)))
				{				
					response.setResponse("Mein Jung, you didn't add a valid faplink! Try again!");
					return response;
				}
				else {
					try {
						response.setResponse("Your fap was added! Here's a complementary fap for you, good sir: " + getRandomFap());
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					addFap(event.info.substring(8));
					Event reactionEvent = new FapEvent(event.info.substring(8));
					response.addEvent(reactionEvent);
					return response;
				}
			}
			
			else if(startsWithLowerCase(event.info, "fapfap"))
			{
				try {
					response.setResponse("Here's a fap for you, good sir: " + getRandomFap());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return response;
			}
			
		}
		return null;
	}
	
	private void addFap(String newFapLink) {
		 File file = new File("./", "fapswa.dat");
		 try {
			BufferedReader r = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			try {
				int newNumberOfFaps = 1 + Integer.parseInt(r.readLine());
				sb.append(newNumberOfFaps+"\r\n");
				String fapLink = r.readLine();
				while(fapLink != null)
				{
					sb.append(fapLink+"\r\n");
					fapLink = r.readLine();
				}
				sb.append(newFapLink);
				r.close();
				file.delete();
				file = null;
				file = new File("./", "fapswa.dat");
				file.createNewFile();
				BufferedWriter w = new BufferedWriter(new FileWriter(file));
				
				w.write(sb.toString());
				w.flush();
	            w.close();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public String getRandomFap() throws IOException
	{
		//open het fapswapdatabaselinkbestand
	    File file = new File("./", "fapswa.dat");
	    String fapToBeReturned = null;
	    //bestaat ie niet?
	    if (!file.isFile())
	    {
	    	//probeer m te maken
	    	if(!file.createNewFile()){
		        throw new IOException("Error creating new file: " + file.getAbsolutePath());
	    	}
	    	//schrijf 0 naar het bestand.
	    	else
	    	{
	    		BufferedWriter writer = null;
	    		try {
	    		    writer = new BufferedWriter(new FileWriter(file));
	    		    writer.write("0");                         
	    		}
	    		catch (IOException e1) {e1.printStackTrace();}
	    		catch (RuntimeException r1) {r1.printStackTrace();}
	    		finally {
	    		    try {
	    		        if(writer != null){
	    		            writer.flush();
	    		            writer.close();
	    		        }
	    		    }
	    		    catch (IOException e2) {e2.printStackTrace();}
	    		}
	    	}
	    }
	    
	    //return een random fap.
	    BufferedReader r = new BufferedReader(new FileReader(file));
	    try {
	        String numberString = r.readLine();
	        int numberOfFaps = Integer.parseInt(numberString);
	        if(numberOfFaps == 0)
	        {
	        	return "Helaas, er zijn nog geen faps!";
	        }
	        int fapToBeReturnedIndex = new Random().nextInt(numberOfFaps);
	        
	      
	        
	        for(int i =0; i <= fapToBeReturnedIndex; i++)
	        {
	        	fapToBeReturned = r.readLine();
	        }
	    } finally{
	        r.close();
	    }
	    
	    return fapToBeReturned;
	}
	
	private boolean validFapLink(String possibleFapLink) {
		try {
			URL url = new URL(possibleFapLink);
		} catch (MalformedURLException e) {
			return false;
		}
		
		return true;
	}

	private boolean startsWithLowerCase(String info, String prefix)
	{
		return info.toLowerCase().startsWith(prefix);
	}

	
}
