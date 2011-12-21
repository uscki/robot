import hypermedia.video.OpenCV;
import processing.core.PApplet;


public class AverageColor extends PApplet {

	OpenCV opencv;
//	GSCapture cam;
	float red;
	float green;
	float blue;
	
	public void setup(){
		size(320,240); 
//	    String[] devices = GSCapture.list();
//	    println(devices);
//	    cam = new GSCapture(this, 320, 240  );
		opencv = new OpenCV(this);
	    opencv.allocate(320,240);
	    println("Calculating average color...");
	}
	
	public void draw(){
//		cam.read();
//		image(cam, 0, 0);
//	    opencv.copy(cam);
	    red = 0;
		green = 0;
		blue = 0;
		loadPixels();
		for(int i = 0; i < pixels.length; i++){
			red += (pixels[i] >> 16) & 0xFF;
			green += (pixels[i] >> 8) & 0xFF;
			blue += pixels[i] & 0xFF;
		}
		red /= pixels.length;
		green /= pixels.length;
		blue /= pixels.length;
		fill(red,green,blue);
		rect(0,0,width,height);
		noFill();
	}
	
}
