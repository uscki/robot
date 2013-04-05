package mennov1;

import static com.googlecode.javacv.cpp.opencv_highgui.cvSaveImage;
import lib.SewerSender;

import com.googlecode.javacv.FrameGrabber;
import com.googlecode.javacv.OpenCVFrameGrabber;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

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
            while (true) {
                img = grabber.grab();
                if (img != null) {
                    cvSaveImage("webcam/capture.jpg", img);
                    try {
                    	Process process = Runtime.getRuntime().exec("webcam/upload");
                    	if (0 != process.waitFor()) {
                    		SewerSender.println("Uploading exited weird!");
                    	}
                    	process.destroy();
                    } catch (Exception ex) { System.out.println(ex); }
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
