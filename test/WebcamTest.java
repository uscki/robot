import hypermedia.video.OpenCV;

import java.awt.Rectangle;

import processing.core.PApplet;
import processing.video.Capture;

/*
 * 
 * OpenCV moet runnen met native library = waar je de processing lib hebt geinstalleerd
 * RMB op Referenced Libraries/OpenCV.jar -> Properties -> Native Library
 * Mac: <Applications>/Processing.app/Contents/Resources/Java/modes/java/libraries/OpenCV/library
 * 
 * Hij is behoorlijk traag
 * 
 */

public class WebcamTest extends PApplet {


OpenCV opencv;
Capture cam;
int angle;        // Data received from the serial port
int time;

public void setup() {
    size( 320, 240 );

    // Dit moet voor opencv staan (magic)
    String[] devices = Capture.list();
    println(devices);
    cam = new Capture(this, 320, 240  ); //, devices[0]);

    
    opencv = new OpenCV(this);
    opencv.allocate(320,240);
    opencv.cascade( OpenCV.CASCADE_FRONTALFACE_ALT );    // load the FRONTALFACE description file
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
    
}
}