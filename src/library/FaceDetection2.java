package library;

import static org.bytedeco.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static org.bytedeco.javacv.cpp.opencv_core.cvClearMemStorage;
import static org.bytedeco.javacv.cpp.opencv_core.cvGetSeqElem;
import static org.bytedeco.javacv.cpp.opencv_core.cvLoad;
import static org.bytedeco.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static org.bytedeco.javacv.cpp.opencv_imgproc.CV_INTER_LINEAR;
import static org.bytedeco.javacv.cpp.opencv_imgproc.cvCvtColor;
import static org.bytedeco.javacv.cpp.opencv_imgproc.cvEqualizeHist;
import static org.bytedeco.javacv.cpp.opencv_imgproc.cvResize;
import static org.bytedeco.javacv.cpp.opencv_objdetect.CV_HAAR_DO_CANNY_PRUNING;
import static org.bytedeco.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;

import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bytedeco.javacpp.Loader;
import org.bytedeco.javacv.cpp.opencv_core.CvMemStorage;
import org.bytedeco.javacv.cpp.opencv_core.CvRect;
import org.bytedeco.javacv.cpp.opencv_core.CvSeq;
import org.bytedeco.javacv.cpp.opencv_core.IplImage;
import org.bytedeco.javacv.cpp.opencv_objdetect;
import org.bytedeco.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;


public class FaceDetection2 {

	private String cascade_file;
	private CvHaarClassifierCascade cascade;
	private CvMemStorage storage;
	private static final int SCALE = 2;

	public FaceDetection2() {
	    // Omdat de .jar is gemaakt met dit bestand erin, is het een resource die we kunnen inladen
		cascade_file = "/haarcascade_frontalface_alt.xml";
		try {
			File classifierFile = Loader.extractResource(cascade_file, null, "classifier", ".xml");
			if (classifierFile == null || classifierFile.length() <= 0) {
				throw new IOException("Could not extract \"" + cascade_file + "\" from Java resources.");
			}
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

	/**
	 * Gezichten vinden in een OpenCV-plaatje
	 * @param origImg het opencv-plaatje om gezichten in te zoeken
	 */
	public List<Rectangle> findFaces(IplImage origImg) {
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
		CvSeq faces = cvHaarDetectObjects(equImg, cascade, storage,1.1, 3, CV_HAAR_DO_CANNY_PRUNING);
		cvClearMemStorage(storage);
		// draw thick yellow rectangles around all the faces
		int total = faces.total();
		List<Rectangle> faceList = new ArrayList<Rectangle>();
		for (int i = 0; i < total; i++) {
			CvRect r = new CvRect(cvGetSeqElem(faces, i));
			faceList.add(new Rectangle(r.x()*SCALE, r.y()*SCALE, r.width()*SCALE, r.height()*SCALE));
		}
		return faceList;
	}



}