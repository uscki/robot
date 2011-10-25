import processing.core.*; 
import processing.video.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class GettingStartedCapture extends PApplet {

/**
 * Getting Started with Capture.
 * 
 * Reading and displaying an image from an attached Capture device. 
 */ 
 
	/*
	 * 
	 * Configure Eclipse to run in 32-bit mode
	 * 
	 * 
	 */


Capture cam;

public void setup() {
  size(640, 480);

  // If no device is specified, will just use the default.
  //cam = new Capture(this, 320, 240);

  // To use another device (i.e. if the default device causes an error),  
  // list all available capture devices to the console to find your camera.
  String[] devices = Capture.list();
  println(devices);
  
  // Change devices[0] to the proper index for your camera.
  cam = new Capture(this, width, height);

  // Opens the settings page for this capture device.
  //camera.settings();
}


public void draw() {
  if (cam.available() == true) {
    cam.read();
    image(cam, 160, 100);
    // The following does the same, and is faster when just drawing the image
    // without any additional resizing, transformations, or tint.
    //set(160, 100, cam);
  }
} 
}
