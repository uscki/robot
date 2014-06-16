import static org.bytedeco.javacv.cpp.opencv_highgui.cvSaveImage;

import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.javacv.cpp.opencv_core.IplImage;

public class GrabberSave implements Runnable {
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
                } else {
                	System.out.println("Nongrab");
                }
                Thread.sleep(INTERVAL);
            }
        } catch (Exception e) { }
    }

    public static void main(String[] args) {
    	System.out.println("Testing");
        Thread g = new Thread(new GrabberSave());
        g.start();
    }
}