package mennov1;

import java.io.File;
import java.io.IOException;
import com.googlecode.javacv.*;
import com.googlecode.javacv.cpp.*;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacpp.Loader;
import static com.googlecode.javacv.cpp.opencv_core.*;
import static com.googlecode.javacv.cpp.opencv_imgproc.*;
import static com.googlecode.javacv.cpp.opencv_highgui.*;
import static com.googlecode.javacv.cpp.opencv_objdetect.*;

import java.util.EventObject;
import events.OpenCVPictureEvent;

/**
 * Neem OpenCVPictureEvents en zoek naar gezichten.
 *
 * @todo: Gezichten zoeken in andere foto's (zoals de Media-DB) mogelijk maken. Misschien gewoon in een library.
 */

public class FaceDetector implements Listener<OpenCVPictureEvent> {

  private String fname;
  private String cascade_file;
  private CvHaarClassifierCascade cascade;
  private CvMemStorage storage;

  private static final int SCALE = 2;

  public FaceDetector(String fname) {
    this.fname = fname;
    // Omdat de .jar is gemaakt met dit bestand erin, is het een resource die we kunnen inladen
    cascade_file = "/haarcascade_frontalface_alt.xml";
    try {
      File classifierFile = Loader.extractResource(cascade_file, null, "classifier", ".xml");
      if (classifierFile == null || classifierFile.length() <= 0) {
          throw new IOException("Could not extract \"" + cascade_file + "\" from Java resources.");
      }
      System.out.println("Loading face classifier from " + classifierFile.getAbsolutePath());

      // Preload the opencv_objdetect module to work around a known bug.
      Loader.load(opencv_objdetect.class);
      cascade = new CvHaarClassifierCascade(cvLoad(classifierFile.getAbsolutePath()));
      classifierFile.delete();
      if (cascade.isNull()) {
          throw new IOException("Could not load the classifier file.");
      }

      storage = CvMemStorage.create();
    } catch (Exception ex) { System.err.println(ex.toString()); }

    // create temp storage, used during object detection
    storage = CvMemStorage.create();
  }

  @Override
  public Boolean wants(EventObject e) {
    return (e instanceof OpenCVPictureEvent);
  }

  @Override
  public void event(OpenCVPictureEvent e) {
    // Sla het plaatje op met doosjes om de gezichten

    IplImage origImg = e.getIplImage();

    // convert to grayscale
    IplImage grayImg = IplImage.create(origImg.width(),origImg.height(), IPL_DEPTH_8U, 1);
    cvCvtColor(origImg, grayImg, CV_BGR2GRAY);

    // scale the grayscale (to speed up face detection)
    IplImage smallImg = IplImage.create(grayImg.width()/SCALE,grayImg.height()/SCALE, IPL_DEPTH_8U, 1);
    cvResize(grayImg, smallImg, CV_INTER_LINEAR);

    // equalize the small grayscale
    IplImage equImg = IplImage.create(smallImg.width(),smallImg.height(), IPL_DEPTH_8U, 1);
    cvEqualizeHist(smallImg, equImg);


    // instantiate a classifier cascade for face detection
    System.out.println("Detecting faces...");

    CvSeq faces = cvHaarDetectObjects(equImg, cascade, storage,1.1, 3, CV_HAAR_DO_CANNY_PRUNING);

    cvClearMemStorage(storage);

    // draw thick yellow rectangles around all the faces
    int total = faces.total();
    System.out.println("Found " + total + " face(s)");

    for (int i = 0; i < total; i++) {
      CvRect r = new CvRect(cvGetSeqElem(faces, i));
      cvRectangle(origImg, cvPoint( r.x()*SCALE, r.y()*SCALE ),cvPoint( (r.x() + r.width())*SCALE,(r.y() + r.height())*SCALE ),CvScalar.RED, 6, CV_AA, 0);

      String strRect = String.format("CvRect(%d,%d,%d,%d)", r.x(), r.y(), r.width(), r.height());
      
      System.out.println(strRect);
      //undo image scaling when calculating rect coordinates
    }

    System.out.println("Saving marked-faces version in " + fname);
    cvSaveImage(fname, origImg);
  }

}
