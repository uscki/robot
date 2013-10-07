package mennov1;
import java.util.EventObject;

import library.CustomSASLDigestMD5Mechanism;
import library.Settings;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.bidimap.DualHashBidiMap;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.StreamError;

import events.ReceiveChatEvent;
import events.SendChatEvent;

public class FacebookClient implements Listener<SendChatEvent> {

   public static final String FB_XMPP_HOST = "chat.facebook.com";
   public static final int FB_XMPP_PORT = 5222;

   private ConnectionConfiguration config;
   private XMPPConnection connection;
   private BidiMap friends = new DualHashBidiMap();
   private FBMessageListener fbml;
   
   private static FacebookClient master;
   
   public static FacebookClient getInstance() {
		if (null == master) {
			master = new FacebookClient();
		}
		return master;
   }
   
   private FacebookClient() {
	   String username = Settings.getInstance().get("fb-login");
	   String password = Settings.getInstance().get("fb-password");
	   
	   
	   Boolean retry = true;
	   while (retry) {
		   retry = false;
		   try {
		       System.out.println("Facebook: connecting...");	
		       connect();
		       System.out.println("Facebook: connected. Logging in...");
		       if (!login(username, password)) {
		          System.err.println("Facebook: Access Denied...");
		       }
		       else {
		    	   System.out.println("Facebook: Logged in to " + username);
		       }
		       getFriends();
		   } catch (XMPPException e) {
		   	System.err.println("XMPPException");
		        if (e.getXMPPError() != null) {
		           System.err.println("ERROR-CODE : " + e.getXMPPError().getCode());
		           System.err.println("ERROR-CONDITION : " + e.getXMPPError().getCondition());
		           System.err.println("ERROR-MESSAGE : " + e.getXMPPError().getMessage());
		           System.err.println("ERROR-TYPE : " + e.getXMPPError().getType());
		        }
		        StreamError streamError = e.getStreamError();
		        if (streamError != null) {
		        	if (streamError.getCode().equals("not-authorized")) {
		        		System.out.println("Facebook: Login failed. Retrying...");
		        		retry = true;
		        	}
		        }
		        disconnect();
		   } catch (Exception ex) {
		   	System.err.println("Other exception");
		   	ex.printStackTrace();
		   }
   	   }
   }

   public String connect() throws XMPPException {
      config = new ConnectionConfiguration(FB_XMPP_HOST, FB_XMPP_PORT);
      SASLAuthentication.registerSASLMechanism("DIGEST-MD5"
        , CustomSASLDigestMD5Mechanism.class);
      config.setSASLAuthenticationEnabled(true);
      config.setDebuggerEnabled(false);
      connection = new XMPPConnection(config);
      connection.connect();
      fbml = new FBMessageListener(connection);
      return connection.getConnectionID();
   }

   public void disconnect() {
      if ((connection != null) && (connection.isConnected())) {
         Presence presence = new Presence(Presence.Type.unavailable);
         presence.setStatus("offline");
         connection.disconnect(presence);
      }
   }

   public boolean login(String userName, String password) 
     throws XMPPException {
      if ((connection != null) && (connection.isConnected())) {
         connection.login(userName, password);
         return true;
      }
      return false;
   }

   public void getFriends() {
      if ((connection != null) && (connection.isConnected())) {
         Roster roster = connection.getRoster();
         int i = 1;
         for (RosterEntry entry : roster.getEntries()) {
            Presence presence = roster.getPresence(entry.getUser());
            if ((presence != null) 
               && (presence.getType() != Presence.Type.unavailable)) {
               friends.put("#" + i, entry);
//               System.out.println(entry.getName() + "(#" + i + ")");
               i++;
            }
         }
         fbml.setFriends(friends);
      }
   }

   public void sendMessage(final RosterEntry friend, String text) 
     throws XMPPException {
      if ((connection != null) && (connection.isConnected())) {
         ChatManager chatManager = connection.getChatManager();
         Chat chat = chatManager.createChat(friend.getUser(), fbml);
         chat.sendMessage(text);
      }
   }
   
   @Override
   public Boolean wants(EventObject e) {
   	return (e instanceof SendChatEvent);
   }

   @Override
   public void event(SendChatEvent e) {
   	if (this == e.client) {
   		try {
   			sendMessage((RosterEntry) friends.get(e.receiver), e.message);
   		} catch (XMPPException ex) {
   			ex.printStackTrace();
   		}
   	}
   }
   
   public class FBMessageListener implements MessageListener, Runnable {

	   private FBMessageListener fbml = this;
	   private XMPPConnection conn;
	   private BidiMap friends;

	   public FBMessageListener(XMPPConnection conn) {
	      this.conn = conn;
	      new Thread(this).start();
	   }

	   public void setFriends(BidiMap friends) {
	      this.friends = friends;
	   }

	   public void processMessage(Chat chat, Message message) {
		   getFriends();
	      MapIterator it = friends.mapIterator();
	      String key = null;
	      RosterEntry entry = null;
	      while (it.hasNext()) {
	         key = (String) it.next();
	         entry = (RosterEntry) it.getValue();
	         if (entry.getUser().equalsIgnoreCase(chat.getParticipant())) {
	            break;
	         }
	      }
	      if ((message != null) && (message.getBody() != null)) {
	    	 
	    	  	// Someone said something! Raise a chat event!
				EventBus.getInstance().event(new ReceiveChatEvent(
						master,
						master,
						key,
						message.getBody()));
				
				
	      }
	   }

	   public void run() {
	      conn.getChatManager().addChatListener(
	         new ChatManagerListener() {
	            public void chatCreated(Chat chat, boolean createdLocally) {
	               if (!createdLocally) {
	                  chat.addMessageListener(fbml);
	               }
	            }
	         }
	      );
	   }
	}
}
