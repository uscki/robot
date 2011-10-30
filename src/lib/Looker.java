package lib;

import mennov1.*;
import processing.video.Capture;
import hypermedia.video.OpenCV;

import java.awt.Rectangle;

public class Looker {
	
	private static MennoV1 p; // The parent PApplet that we will render ourselves onto
	private static Looker master;
	
	private Rectangle[] faces;
	OpenCV opencv;
	Capture cam;
	
	public Looker(MennoV1 parent) {
		master = this;
		p = parent;
		
		p.println("Started looker...");
		String[] devices = Capture.list();
	    p.println(devices);
	    cam = new Capture(p, 320, 240  ); //, devices[0]);

	    
	    opencv = new OpenCV(p);
	    opencv.allocate(320,240);
	    opencv.cascade( OpenCV.CASCADE_FRONTALFACE_ALT );    // load the FRONTALFACE description file
	}
	
	// Het is een singleton pattern
	public static Looker getInstance() {
		if(master == null)
			return null; // throw error?
		return master;
	}
	
	public Boolean seesFace() {
		p.println("Ik zie ");
		Boolean sees = (faces.length > 0);
		p.println((sees? "wel" : "niet") + " een gezicht");
		return sees;
	}
	
	public void look() {
	    cam.read();
	    p.image(cam, 0, 0);
	    opencv.copy(cam);
	    
	    // detect anything ressembling a FRONTALFACE
	    faces = opencv.detect();
	    
	    // draw detected face area(s)
	    p.noFill();
	    p.stroke(255,0,0);
	    for( int i=0; i<faces.length; i++ ) {
	    	p.rect( faces[i].x, faces[i].y, faces[i].width, faces[i].height ); 
	    }
	}
	
}
