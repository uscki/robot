

import java.io.IOException;
import java.util.*;
import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.*;

public class SmackTest {

	private static String mypartner;

	public static class MessageParrot implements MessageListener {



		// gtalk seems to refuse non-chat messages
		// messages without bodies seem to be caused by things like typing
		public void processMessage(Chat chat, Message message) {
			Message msg = new Message(mypartner, Message.Type.chat);

			if(message.getType().equals(Message.Type.chat) && message.getBody() != null) {
				String body = message.getBody();
				System.out.println("Received: " + body);
				try {
					String output = "Je moeder is " + body;
					msg.setBody(output);
					System.out.println("Sent: " + output);
					chat.sendMessage(msg);
				} catch (XMPPException ex) {
					//ex.printStackTrace();
					System.out.println("Failed to send message");
				}
			} else {
				System.out.println("I got a message I didn\'t understand");
			}
		}
	}


	public static void main( String[] args ) {
		mypartner = "j.t.baumfalk@gmail.com";

		System.out.println("Starting IM client");

		// gtalk requires this or your messages bounce back as errors
		XMPPConnection connection = new XMPPConnection("gmail.com");

		try {
			connection.connect();
			System.out.println("Connected to " + connection.getHost());
		} catch (XMPPException ex) {
			//ex.printStackTrace();
			System.out.println("Failed to connect to " + connection.getHost());
			System.exit(1);
		}
		try {
			connection.login("mennov1@vinnl.nl", "appelflap");
			System.out.println("Logged in as " + connection.getUser());

			Presence presence = new Presence(Presence.Type.available);
			connection.sendPacket(presence);

			// Get the user's roster
			Roster roster = connection.getRoster();

			// Print the number of contacts
			System.out.println("Number of contacts: " + roster.getEntryCount());

			// Enumerate all contacts in the user's roster
			for (RosterEntry entry : roster.getEntries())
			{
				System.out.println(entry.getName() + ": " + (roster.getPresences(entry.getUser()).next().isAvailable()?"on":"off") + "line ["+entry.getUser()+"]");
			}

		} catch (XMPPException ex) {
			//ex.printStackTrace();
			System.out.println("Failed to log in as " + connection.getUser());
			System.exit(1);
		}

		ChatManager chatmanager = connection.getChatManager();
		Chat chat = chatmanager.createChat(mypartner, new MessageParrot());

		try {
			// google bounces back the default message types, you must use chat
			Message msg = new Message(mypartner, Message.Type.chat);
			msg.setBody("Dag, mooie jongen!");
			chat.sendMessage(msg);
		} catch (XMPPException e) {
			System.out.println("Failed to send message");
			// handle this how?
		}

		System.out.println("Press enter to disconnect");

		try {
			System.in.read();
		} catch (IOException ex) {
			//ex.printStackTrace();
		}

		connection.disconnect();  
		System.out.println("Disconnected");
	}
}