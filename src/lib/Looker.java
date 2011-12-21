package lib;

import gifAnimation.GifMaker;
import hypermedia.video.OpenCV;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import mennov1.MennoV1;

import org.gstreamer.Caps;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.swing.VideoComponent;
import org.seltar.Bytes2Web.ImageToWeb;

import processing.core.PImage;

public class Looker {

	private static MennoV1 p; // The parent PApplet that we will render ourselves onto
	private static Looker master;

	private Rectangle[] faces;
	OpenCV opencv;
	PImage[] images;
	GifMaker gif;
	private static Pipeline pipe; 
	public Looker(final JFrame frame) {
		master = this;
		Gst.init("SwingVideoTest", new String[0]); 
		pipe = new Pipeline("pipeline"); 
		// This is from VideoTest example and gives test image 
		// final Element videosrc = 
		ElementFactory.make("videotestsrc", "source"); 
		// This gives black window with VideoComponent 
		final Element videosrc = ElementFactory.make("v4l2src", "source"); 
		final Element videofilter = ElementFactory.make("capsfilter", "flt"); 
		videofilter.setCaps(Caps.fromString("video/x-raw-yuv, width=640, height=480")); 
		SwingUtilities.invokeLater(new Runnable() { 
			public void run() { 
				VideoComponent videoComponent = new VideoComponent(); 
				// This gives only black window 
				Element videosink = videoComponent.getElement(); 
				// This gives 2nd window with stream from webcam 
				// Element videosink = 
				ElementFactory.make("xvimagesink", "sink"); 
				pipe.addMany(videosrc, videofilter, videosink); 
				Element.linkMany(videosrc, videofilter, videosink);  
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
				frame.add(videoComponent, BorderLayout.CENTER); 
				videoComponent.setPreferredSize(new Dimension(640, 480)); 
				frame.pack(); 
				frame.setVisible(true); 
				// Start the pipeline processing 
				pipe.setState(State.PLAYING); 
			} 
		}); 

		//opencv = new OpenCV(p);
		//opencv.allocate(320,240);
		//opencv.cascade( OpenCV.CASCADE_FRONTALFACE_ALT );    // load the FRONTALFACE description file
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
			//images[n] = p.loadImage(filenames[n]);
		}
	}

	public Boolean seesFace() {
		System.out.print("Ik zie ");
		Boolean sees = (faces.length > 0);
		System.out.println((sees? "" : "g") + "een gezicht");
		return sees;
	}

	public void look() {
		/*		cam.read();
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
		 */
	}

	public Rectangle[] getFaces() {	
		return faces;
	}
	public PImage getImages(int i) {
		return images[i];
	}

	public void uploadImage(String imagename){
		String url = "http://www.groepfotoboek.nl/robot/upload.php";
		//ImageToWeb img_to_web = new ImageToWeb(p);
		//img_to_web.post("uploadedfile",url,imagename,true);
	}

	//Delay in milliseconds
	//saved nu naar bin/filename.gif
	public void maakGif(String filename, int frames, int delay) {
		/*		gif = new GifMaker(p,filename);
		gif.setRepeat(0);
		for(int i = 0; i < frames; i++){
			gif.setDelay(delay);
			gif.addFrame();
		}
		gif.finish();*/
	}

	//Returns average RGB of the current screen
	public int[] pixels(){
		/*		float red = 0;
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
		return colors;*/return null;
	}

	//or in a certain area
	public int[] pixels(int x,int y,int w,int h){
		/*		float red = 0;
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
		return colors;*/ return null;
	}

}
