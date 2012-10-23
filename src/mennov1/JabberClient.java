package mennov1;
import java.util.EventObject;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;

import events.ReceiveChatEvent;
import events.SendChatEvent;



public class JabberClient implements Listener<SendChatEvent> {

	private ChatManager chatmanager;
	private static JabberClient master;
	private XMPPConnection connection;

	public static JabberClient getInstance() {
		if (null == master) {
			master = new JabberClient();
		}
		return master;
	}

	private JabberClient() {
		// gtalk requires this or your messages bounce back as errors
		connection = new XMPPConnection("gmail.com");

		// try to connect to the gmail service.
		try {
			connection.connect();
		} catch (XMPPException ex) {
			//ex.printStackTrace();
			System.out.println("Failed to connect to " + connection.getHost());
		}

		// try to log in.
		try {
			System.out.println("Jabber: Logged in to gmail.com");
			connection.login("mennov1@vinnl.nl", "appelflap");
			// set our presence as available.
			connection.sendPacket(new Presence(Presence.Type.available));
			// Accept only messages from HQ
			new AndFilter(new PacketTypeFilter(Message.class));
			// Register the listener.
			PacketListener myListener = new PacketListener() {
				// A packetlistener processes packets
				public void processPacket(Packet packet) {
					if (packet instanceof Message) {
						Message msg = (Message) packet;
						if(msg.getType().equals(Message.Type.chat) && msg.getBody() != null) {

							// Someone said something! Raise a chat event!
							EventBus.getInstance().event(new ReceiveChatEvent(
									master,
									master,
									msg.getFrom(),
									msg.getBody()));
						}
					}
					if (packet instanceof Presence) {
						Presence wantsToSubscribe = ((Presence) packet); 
						// Someone wants to be friends with us
						if (wantsToSubscribe.getType().equals(Presence.Type.subscribe)) {
							Presence subscribed = new Presence(Presence.Type.subscribed);
							subscribed.setTo(wantsToSubscribe.getFrom());
							connection.sendPacket(subscribed);
							
							Presence subscribe = new Presence(Presence.Type.subscribe);
							subscribe.setTo(wantsToSubscribe.getFrom());
							connection.sendPacket(subscribe);
						}
					}
				}
			};
			connection.addPacketListener(myListener, null);
		} catch (XMPPException ex) {
			//ex.printStackTrace();
			System.out.println("Failed to log in as " + connection.getUser());
			disconnect();
			EventBus.getInstance().removeListener(this);
		}

		chatmanager = connection.getChatManager();
	}
	public void disconnect() {
		connection.disconnect();
	}

	public void event(SendChatEvent e) {
		if (this == e.client) {
			try {
				chatmanager.createChat(e.receiver, null).sendMessage(e.message);
			} catch (XMPPException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public Boolean wants(EventObject e) {
		return (e instanceof SendChatEvent);
	}
}
