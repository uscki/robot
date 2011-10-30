import hypermedia.video.OpenCV;

import java.awt.Rectangle;

import processing.core.*;
import processing.serial.Serial;
import processing.video.*;

/**
 * 
 * Plak een snor op een herkend gezicht via OpenCV
 * 
 * @author Sjoerd
 *
 */

public class SnorBot extends PApplet {
	
	OpenCV opencv;
	Capture cam;
	PImage snor;
	
	public void setup(){
		size(350,250); //venster eigenlijk niet nodig?
	    String[] devices = Capture.list();
	    println(devices);
	    cam = new Capture(this, 320, 240  ); //, devices[0]);
		//load plaatje in
		snor = loadImage("snor.png");
		
		opencv = new OpenCV(this);
	    opencv.allocate(320,240);
	    opencv.cascade( OpenCV.CASCADE_FRONTALFACE_ALT );  
	    
		println("Brushing 'stache... please wait...");
	}
	
	public void draw() {
		cam.read();
	    image(cam, 0, 0);
	    opencv.copy(cam);
	    
	    Rectangle[] faces = opencv.detect();
	    
	    if(faces.length != 0){
	    	int w = faces[0].width;
	    	int h = faces[0].height;
	    	int x = faces[0].x + (w/4);
	    	int y = faces[0].y + (h/2) + (h/8);
	    	w = w/2;
	    	h = h/4;
	    	image(snor,x, y, w, h ); 
	    }
		PImage upload = get();
		image(upload,0,0);
		//gebruik postToWeb om plaatje op de server te zetten

		//en geef een link terug
	}

}
