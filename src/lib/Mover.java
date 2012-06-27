package lib;

import mennov1.*;
import processing.core.*;
import processing.serial.*;

public class Mover {
	private static MennoV1 p; // The parent PApplet that we will render ourselves onto
	private static Mover master;
	Serial myPort;  // Create object from Serial class
	
	int angle;
	int pitch;
	
	public static Mover getInstance() {
		if(master == null)
			master =  new Mover(); // throw error?
		return master;
	}

	public Mover() {
		String portName = Serial.list()[0];
		System.out.println("Serial things: \n" + Serial.list());
		myPort = new Serial(null, portName, 9600);		
		angle = 60;
		pitch = 20;
		myPort.clear();
		myPort.write(angle + 20);
		myPort.write(pitch + 20);
	}

	public void moveTo(int x, int y, int width, int height) {
		int face_angle = (x - width/2)/8;
		angle += face_angle;
		
		int face_pitch = (y - height/2)/8;
		pitch += face_pitch;

		myPort.clear();
		System.out.println("Mover draait naar (hoek:" + angle + ", hoogte:" + pitch + ")");
		myPort.write(angle + 20);
		myPort.write(pitch + 20);
	}

	public int moveToInterval(int x, int y, int width, int height, int time, int interval) {
		if (interval != 0) {
			if (0 == interval-time) {
				moveTo(x, y, width, height);
				return 0;
			}
			return time+1;
		} else return 0;
	}

}
