package mennov1;

import java.io.IOException;
import java.util.ArrayList;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;


/**
 * 
 * @author Jetze Baumfalk, Benno Kruit
 * @category Framework
 *
 * A basic implementation of a Jabber Client which can communicate with MennoV1.
 */

public class JabberClient implements Runnable{

	private ChatManager chatmanager;
	
	public JabberClient() {
	}
	
	
	public void runClient() {
		System.out.println("Starting IM client");

		// gtalk requires this or your messages bounce back as errors
		XMPPConnection connection = new XMPPConnection("gmail.com");

		// try to connect to the gmail service.
		try {
			connection.connect();
			System.out.println("Connected to " + connection.getHost());
		} catch (XMPPException ex) {
			//ex.printStackTrace();
			System.out.println("Failed to connect to " + connection.getHost());
		}
		
		// try to log in.
		try {
			connection.login("mennov1@vinnl.nl", "appelflap");
			System.out.println("Logged in as " + connection.getUser());

			// set our presence as available.
			Presence presence = new Presence(Presence.Type.available);
			connection.sendPacket(presence);

			// Get the user's contact roster
			Roster roster = connection.getRoster();

			// Print the number of contacts
			System.out.println("Number of contacts: " + roster.getEntryCount());

			// Enumerate all contacts in the user's contact roster
			for (RosterEntry entry : roster.getEntries()) {
				System.out.println(entry.getName() + ": " + (roster.getPresences(entry.getUser()).next().isAvailable()?"on":"off") + "line ["+entry.getUser()+"]");
			}

			 // Accept only messages from HQ
			PacketFilter filter   = new AndFilter(new PacketTypeFilter(Message.class));
		 
		    // Register the listener.
		    connection.addPacketListener(myListener, null);
			
		} catch (XMPPException ex) {
			//ex.printStackTrace();
			System.out.println("Failed to log in as " + connection.getUser());
			System.exit(1);
		}

		chatmanager = connection.getChatManager();

		System.out.println("Press enter to disconnect");

		try {
			System.in.read();
		} catch (IOException ex) {
			//ex.printStackTrace();
		}

		connection.disconnect();  
		System.out.println("Disconnected");
	}
	
	
	public static void main( String[] args ) {
		JabberClient temp = new JabberClient();
		temp.runClient();
	}
	
	
PacketListener myListener = new PacketListener() {
	public void processPacket(Packet packet) {
		if (packet instanceof Message) {
			Message msg = (Message) packet;
			Chat chat = chatmanager.createChat(msg.getFrom(), new MessageParrot());
		}
	}
};
	
	public static class MessageParrot implements MessageListener {

		// gtalk seems to refuse non-chat messages
		// messages without bodies seem to be caused by things like typing
		public void processMessage(Chat chat, Message message) {
			if(message.getType().equals(Message.Type.chat) && message.getBody() != null) {
				String body = message.getBody();
				ArrayList <String> outputs = BotHandler.getInstance().parseArguments(body, message.getFrom());
				System.out.println(message.getFrom() + ": " +body);
				try {
					StringBuilder builder = new StringBuilder();
					for(String s : outputs) {
						if(null == s){
							continue;
						}
						System.out.println("Sent: " + s);
						chat.sendMessage(s);
					}
				} catch (XMPPException ex) {
					//ex.printStackTrace();
					System.out.println("Failed to send message");
				}
			} else {
				System.out.println("I got a message I didn\'t understand");
			}
		}
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		runClient();
	}

}
