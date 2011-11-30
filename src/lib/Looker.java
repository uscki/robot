package lib;

import mennov1.*;
import processing.core.*;
import processing.video.*;
import hypermedia.video.OpenCV;

import java.awt.Rectangle;
import gifAnimation.*; 
import org.seltar.Bytes2Web.ImageToWeb;

public class Looker {
	
	private static MennoV1 p; // The parent PApplet that we will render ourselves onto
	private static Looker master;
	
	private Rectangle[] faces;
	OpenCV opencv;
	Capture cam;
	PImage[] images;
	GifMaker gif;
	
	public Looker(MennoV1 parent) {
		master = this;
		p = parent;
		
		p.println("Started looker...");
		String[] devices = Capture.list();
	    p.println(devices);
	    cam = new Capture(p, 320, 240); //, devices[0]); // Om te testen op andere webcam dit veranderen
	    
	    
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
	public MennoV1 getPApplet() {
		return p;
	}
	
	//alleen beetje gek voor 1 plaatje
	public void plaatjes(String[] filenames){
		images = new PImage[filenames.length];
		
		for(int n = 0; n <images.length; n++){
			images[n] = p.loadImage(filenames[n]);
		}
	}
	
	public Boolean seesFace() {
		p.print("Ik zie ");
		Boolean sees = (faces.length > 0);
		p.println((sees? "" : "g") + "een gezicht");
		return sees;
	}
	
	public void look() {
	    cam.read();
	    p.image(cam, 0, 0);
	    opencv.copy(cam);
	    
	    // Draai het plaatje
	    PImage img = p.createImage(p.IWIDTH, p.IHEIGHT, p.RGB);
	    img.loadPixels();
	    for (int i = 0; i < p.IWIDTH; i++) {
	      for (int j = 0; j < p.IHEIGHT; j++) {
	        int loc1 = (cam.width - j - 1) + i*cam.width;
	       // int loc2 = (img.width - i - 1) + j*img.width;
	        int loc2 = (i) + j*img.width;
	        
	        img.pixels[img.pixels.length - loc2 - 1] = cam.pixels[loc1];
	      }
	    }
	    img.updatePixels();
	    
	    // detect anything ressembling a FRONTALFACE
	    faces = opencv.detect();
	    
	}
	
	public Rectangle[] getFaces() {	
		return faces;
	}
	public PImage getImages(int i) {
		return images[i];
	}
	
	public void uploadImage(String imagename){
		String url = "http://www.groepfotoboek.nl/robot/upload.php";
	    ImageToWeb img_to_web = new ImageToWeb(p);
	    img_to_web.post("uploadedfile",url,imagename,true);
	}
	
	//Delay in milliseconds
	//saved nu naar bin/filename.gif
	public void maakGif(String filename, int frames, int delay) {
		gif = new GifMaker(p,filename);
		gif.setRepeat(0);
		for(int i = 0; i < frames; i++){
			gif.setDelay(delay);
			gif.addFrame();
		}
		gif.finish();
	}
	
	//Returns average RGB of the current screen
	public int[] pixels(){
		float red = 0;
		float green = 0;
		float blue = 0;
		p.loadPixels();
		for(int i = 0; i < p.pixels.length; i++){
			red += (p.pixels[i] >> 16) & 0xFF;
			green += (p.pixels[i] >> 8) & 0xFF;
			blue += p.pixels[i] & 0xFF;
		}
		red /= p.pixels.length;
		green /= p.pixels.length;
		blue /= p.pixels.length;
		int[] colors = {(int)red,(int)green,(int)blue};
		return colors;
	}
	
	//or in a certain area
	public int[] pixels(int x,int y,int w,int h){
		float red = 0;
		float green = 0;
		float blue = 0;
		if(w > p.width){
			w = p.width;
		}
		if(h > p.height){
			h = p.height;
		}
		int temp = p.width + x + 1;
		p.loadPixels();
		for (int i = y; i < y+h ; i++){
			for(int j = i*p.width + x + 1 ; j < i*p.width + x + 1 + w ; j++){
				red += (p.pixels[j] >> 16) & 0xFF;
				green += (p.pixels[j] >> 8) & 0xFF;
				blue += p.pixels[j] & 0xFF;
			}
		}
		red /= w*h;
		green /= w*h;
		blue /= w*h;
		int[] colors = {(int)red,(int)green,(int)blue};
		return colors;
	}
	
}
