import mennov1.*;
import processing.core.*;

public class ProcessingAndThreadsTest extends PApplet {
	
	
	public void setup() {
		size(200,200);
		
		println("hello world");

		
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
		ellipse(56, 46, 55, 55);
	}
}