package mennov1;

public class MasterClient {
	public static void main(String [] args) {
		 Thread [] clients = new Thread[3];
		 clients[0] = new Thread(new IrcClient());
		 clients[1] = new Thread(new JabberClient());
		 clients[2] = new Thread(new TerminalClient());
		 for(Thread client : clients) {
			 client.start();
		 }
	}
}
