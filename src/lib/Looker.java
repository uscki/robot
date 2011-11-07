package lib;

import mennov1.*;
import processing.core.*;
import processing.video.*;
import hypermedia.video.OpenCV;

import java.awt.Rectangle;

import org.seltar.Bytes2Web.ImageToWeb;

public class Looker {
	
	private static MennoV1 p; // The parent PApplet that we will render ourselves onto
	private static Looker master;
	
	private Rectangle[] faces;
	OpenCV opencv;
	Capture cam;
	PImage[] images;
	
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
	
	//alleen beetje gek voor 1 plaatje
	public void plaatjes(String[] filenames){
		images = new PImage[filenames.length];
		
		for(int n = 0; n <images.length; n++){
			images[n] = p.loadImage(filenames[n]);
		}
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
	    
	}
	
	public Rectangle[] getFaces() {	
		return faces;
	}
	public PImage getImages(int i) {
		return images[i];
	}
	
	public void geenFill(){
		p.noFill();
	}
	
	public void tekenLijn(int x, int y, int z){
		p.stroke(x,y,z);
	}
	
	public void tekenRechthoek(int x, int y, int w, int h){
		p.rect(x,y,w,h);
	}
	
	public void tekenPlaatje(PImage i, int x, int y, int w, int h){
		if(w == 0 && h == 0) {
			p.image(i,x,y);
		} else {
			p.image(i,x,y,w,h);
		}
	}
	
	public void uploadImage(String imagename){
		String url = "http://www.groepfotoboek.nl/robot/upload.php";
	    ImageToWeb img_to_web = new ImageToWeb(p);
	    img_to_web.post("uploadedfile",url,imagename,true);
	}
	
}
