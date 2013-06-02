package mennov1;

import library.SewerSender;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import events.OpenCVPictureEvent;

/**
 * Neem webcam-foto's met OpenCV, en maak er PictureEvents van.
 */


public class WebcamClient implements Runnable {
    final int INTERVAL=5000;///you may use interval
    IplImage image;
    @Override
    public void run() {
        try {
        	FrameGrabber grabber = new OpenCVFrameGrabber(0);
            grabber.setImageWidth(640);
            grabber.setImageHeight(480);
            SewerSender.logMessage("Webcam framegrabber bits per pixel: "+grabber.getBitsPerPixel());
            grabber.start();
            IplImage img;
            SewerSender.logMessage("Started webcam");
            System.out.println("Started webcam");
            while (true) {
                img = grabber.grab();
                if (img != null) {
                    // Lanceer een Picture Event, want er is beeld!
                    // Nu moet het door ImageUploader naar uscki.nl worden gestuurd.
                    EventBus.getInstance().event(new OpenCVPictureEvent(this, img));                     
                } else {
                	System.out.println("Webcam frame is null");
                }
                Thread.sleep(INTERVAL);
            }
        } catch (Exception e) {
        	SewerSender.println("webcam kapot huilen!\n"+e.toString());
        }
    }
}
