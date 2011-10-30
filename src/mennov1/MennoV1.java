package mennov1;


import lib.Looker;
import processing.core.*;

public class MennoV1 extends PApplet {

	
	public void setup() {
		println("Started MennoV1");
		size(320, 240);
			
		// Initialize libraries
		new Looker(this);
		
		// Initialize threads
		Thread [] clients = new Thread[3];
		clients[0] = new Thread(new IrcClient());
		clients[1] = new Thread(new JabberClient());
		clients[2] = new Thread(new TerminalClient());
		for(Thread client : clients) {
			client.start();
		}
		
		
	}

	public void draw() {
		background(100);
		Looker.getInstance().look();
	}
}