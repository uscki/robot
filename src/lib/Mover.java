package lib;

import mennov1.MennoV1;
import processing.serial.Serial;

public class Mover {
	private static MennoV1 p; // The parent PApplet that we will render ourselves onto
	private static Mover master;
	Serial myPort;  // Create object from Serial class

	int angle;
	int pitch;

	public Mover(MennoV1 parent) {
		master = this;
		p = parent;

//		p.println("Started mover...");
		String portName = Serial.list()[0];
//		p.println(Serial.list());
//		myPort = new Serial(p, portName, 9600);		
		angle = 60;
		pitch = 20;
		myPort.clear();
		myPort.write(angle + 20);
		myPort.write(pitch + 20);
	}

	// Het is een singleton pattern
	public static Mover getInstance() {
		if(master == null)
			return null; // throw error?
		return master;
	}

	public void moveTo(int x, int y) {
		int face_angle = (x - p.IWIDTH/2)/8;
		angle += face_angle;
		
		int face_pitch = (y - p.IHEIGHT/2)/8;
		pitch += face_pitch;

		myPort.clear();
//		p.println("Mover draait naar (hoek:" + angle + ", hoogte:" + pitch + ")");
		myPort.write(angle + 20);
		myPort.write(pitch + 20);
	}

	public int moveToInterval(int x, int y, int time, int interval) {
		if (interval != 0) {
			if (0 == interval-time) {
				moveTo(x, y);
				return 0;
			}
			return time+1;
		} else return 0;
	}

}
