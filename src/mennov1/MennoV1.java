package mennov1;

import lib.Looker;
import processing.core.*;

public class MennoV1 extends PApplet {

	Looker l;
	
	public void setup() {
		println("Started MennoV1");
		size(320, 240);
			
		// Initialize libraries
		l = new Looker(this);
		
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
		l.look();
		l.noFill();
		l.tekenLijn(255, 0, 0);
		for( int i=0; i<l.getFaces().length; i++ ) {
	    	l.tekenRechthoek( l.getFaces()[i].x, l.getFaces()[i].y, l.getFaces()[i].width, l.getFaces()[i].height ); 
		}
	}
}