import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

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
                    cvSaveImage("capture.jpg", img);
                    System.out.println("Grab");
                } else {
                	System.out.println("Nongrab");
                }
                Thread.sleep(INTERVAL);
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
    	System.out.println("testing");
        Thread g = new Thread(new GrabberSave());
        g.start();
    }
}