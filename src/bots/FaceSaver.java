package bots;

import static org.bytedeco.javacv.cpp.opencv_core.CV_AA;
import static org.bytedeco.javacv.cpp.opencv_core.cvPoint;
import static org.bytedeco.javacv.cpp.opencv_core.cvRectangle;
import static org.bytedeco.javacv.cpp.opencv_highgui.cvSaveImage;

import java.awt.Rectangle;
import java.util.EventObject;
import java.util.List;

import library.FaceDetection2;

import org.bytedeco.javacv.cpp.opencv_core.CvScalar;
import org.bytedeco.javacv.cpp.opencv_core.IplImage;

import events.OpenCVPictureEvent;

/**
 * Neem OpenCVPictureEvents, zet doosjes om de gezichten, en slaat plaatjes op
 */

public class FaceSaver implements IBot<OpenCVPictureEvent> {

  private String fname;
  private FaceDetection2 detection;

  public FaceSaver(String fname) {
    this.fname = fname;
    detection = new FaceDetection2();
  }

  @Override
  public Boolean wants(EventObject e) {
    return (e instanceof OpenCVPictureEvent);
  }

  @Override
  public void event(OpenCVPictureEvent e) {
    // Sla het plaatje op met doosjes om de gezichten
    IplImage origImg = e.getIplImage();
    List<Rectangle> faces = detection.findFaces(origImg);
    for (Rectangle r : faces) {
      cvRectangle(origImg, cvPoint( r.x, r.y ),cvPoint( r.x+r.width, r.y+r.height ),CvScalar.RED, 6, CV_AA, 0);
    }

    System.out.println("Saving "+faces.size()+" marked-faces version in " + fname);
    cvSaveImage(fname, origImg);
  }

}
