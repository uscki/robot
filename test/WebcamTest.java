import hypermedia.video.OpenCV;

import java.awt.Rectangle;

import processing.core.PApplet;
import processing.serial.Serial;
import processing.video.Capture;

/*
 * 
 * OpenCV moet runnen met native library = waar je de processing lib heb geinstalleerd
 * RMB op Referenced Libraries/OpenCV.jar -> Properties -> Native Library
 * Mac: <Applications>/Processing.app/Contents/Resources/Java/modes/java/libraries/OpenCV/library
 * 
 * Hij is behoorlijk traag
 * 
 */

public class WebcamTest extends PApplet {


OpenCV opencv;
Capture cam;
Serial myPort;  // Create object from Serial class
int angle;        // Data received from the serial port
int time;

public void setup() {
	
	System.out.println("Wat is deze, vrind");
	System.out.println("java.library.path="+System.getProperty("java.library.path"));
	
    size( 320, 240 );

    // Dit moet voor opencv staan (magic)
    String[] devices = Capture.list();
    println(devices);
    cam = new Capture(this, 320, 240  ); //, devices[0]);

    
    opencv = new OpenCV(this);
    opencv.allocate(320,240);
    opencv.cascade( OpenCV.CASCADE_FRONTALFACE_ALT );    // load the FRONTALFACE description file
    
    
    //String portName = Serial.list()[0];
    //myPort = new Serial(this, portName, 9600);
    angle = 0;
    
    time = 0;
}

public void draw() {
    cam.read();
    image(cam, 0, 0);
    opencv.copy(cam);
    
    // detect anything ressembling a FRONTALFACE
    Rectangle[] faces = opencv.detect();
    
    // draw detected face area(s)
    noFill();
    stroke(255,0,0);
    for( int i=0; i<faces.length; i++ ) {
        rect( faces[i].x, faces[i].y, faces[i].width, faces[i].height ); 
    }
    if (faces.length > 0) {
      if ( (faces[0].x + (faces[0].width/2)) > 180 ) {
        angle += 1; 
      }
      if ( (faces[0].x + (faces[0].width/2)) < 140 ) {
        angle -= 1; 
      }
      //myPort.write(angle);
    }
    
    if (time == 5) {
      //saveFrame("beeld.jpg");
      time = 0;
    }
    time+=1;
    
}
}
