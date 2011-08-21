package nl.uscki.robot.mennov1;

import jabber.SmackTest.MessageParrot;

import java.io.IOException;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;

import bots.Bot;

public class JabberClient {

	private static String mypartner;
	
	static MennoV1 master;
	
	private JabberClient() {

		if(MennoV1.master == null)
			MennoV1.master = new MennoV1();
		master = MennoV1.master;
		run();
	}
	
	
	public void run() {
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
	public static void main( String[] args ) {
		JabberClient temp = new JabberClient();
	}
	
	public static class MessageParrot implements MessageListener {

		// gtalk seems to refuse non-chat messages
		// messages without bodies seem to be caused by things like typing
		public void processMessage(Chat chat, Message message) {
			Message msg = new Message(mypartner, Message.Type.chat);
			
			if(message.getType().equals(Message.Type.chat) && message.getBody() != null) {
				String body = message.getBody();
				String [] outputs = master.parseArguments(body);
				System.out.println("Received: " + body);
				try {
					StringBuilder builder = new StringBuilder();
					for(String s : outputs) {
						builder.append(s);
					}
					String output = builder.toString();
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

}
