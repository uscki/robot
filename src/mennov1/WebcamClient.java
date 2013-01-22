package mennov1;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

public class WebcamClient implements Runnable {
    final int INTERVAL=1000;///you may use interval
    IplImage image;
    @Override
    public void run() {
        FrameGrabber grabber = new OpenCVFrameGrabber(0); 
        try {
            grabber.start();
            IplImage img;
            while (true) {
                img = grabber.grab();
                if (img != null) {
                    cvSaveImage("webcam/capture.jpg", img);
                    System.out.println("Grab");
                    //Just for benno
                    try {
                    	Runtime.getRuntime().exec("webcam/upload");
                    } catch (Exception e) { }
                } else {
                	System.out.println("Nongrab");
                }
                Thread.sleep(INTERVAL);
            }
        } catch (Exception e) { System.out.println("webcam kapot huilen!"); }
    }
}
