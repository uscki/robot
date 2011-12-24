package lib;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvGetSeqElem;
import static com.googlecode.javacv.cpp.opencv_core.cvLoad;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_objdetect.cvHaarDetectObjects;
import gifAnimation.GifMaker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import mennov1.MennoV1.OnResizeListener;

import com.googlecode.javacv.cpp.opencv_core.CvMemStorage;
import com.googlecode.javacv.cpp.opencv_core.CvRect;
import com.googlecode.javacv.cpp.opencv_core.CvSeq;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_objdetect.CvHaarClassifierCascade;


public class Looker {

	private static Looker master;
	private Webcam webcam;

	private ArrayList<CvRect> faces = new ArrayList<CvRect>();
	private GifMaker gif;


	public Looker() throws NoInputAvailableException {
		try {
			webcam = new Webcam();
		} catch (OSNotDetectedException e) {
			throw new NoInputAvailableException("Could not detect OS");
		}
	}

	// Het is een singleton pattern
	public static Looker getInstance() throws NoInputAvailableException {
		if(master == null) master = new Looker();
		return master;
	}

	public Component getComponent(){	
		if(webcam == null) throw new IllegalStateException();
		return webcam.getVideoComponent();
	}
	
	public void setOnResizeListener(OnResizeListener listener){
		webcam.setOnResizeListener(listener);
	}

	//alleen beetje gek voor 1 plaatje
	public void plaatjes(String[] filenames){
		//		images = new PImage[filenames.length];

		//		for(int n = 0; n <images.length; n++){
		//images[n] = p.loadImage(filenames[n]);
		//		}
	}

	public Boolean seesFace() {
		System.out.print("Ik zie ");
		Boolean sees = (faces.size() > 0);
		System.out.println((sees? "" : "g") + "een gezicht");
		return sees;
	}

	public void detectFaces() {
		if(webcam != null){

			BufferedImage bi = webcam.getScreenshot();
			if(bi != null){
				IplImage originalImage = IplImage.createFrom(bi);

				IplImage grayImage = IplImage.create(originalImage.width(), originalImage.height(), IPL_DEPTH_8U, 1);
				cvCvtColor(originalImage, grayImage, CV_BGR2GRAY);

				CvMemStorage storage = CvMemStorage.create();

				CvHaarClassifierCascade cascade = new CvHaarClassifierCascade(cvLoad("/home/richard/SoftDev/opencv/data/haarcascades/haarcascade_frontalface_alt.xml"));
				CvSeq facesTmp = cvHaarDetectObjects(grayImage, cascade, storage, 1.1, 1, 0);

				faces = new ArrayList<CvRect>();
				for (int i = 0; i < facesTmp.total(); i++) {
					CvRect r = new CvRect(cvGetSeqElem(facesTmp, i));
					faces.add(r);
				}
				System.out.println("faces: " + facesTmp.total());
			}
		}
	}

	public void drawFaces(Graphics g){
		g.setColor(Color.RED);
		//g.fillRect(0, 0, 320, 240);
		g.setColor(Color.GREEN);
		g.drawImage(webcam.getScreenshot(), 0, 0, null);
		for(CvRect r : faces){
			
			g.drawRect(r.x(), r.y(), r.width(), r.height());
		}
		
	}

	public ArrayList<CvRect> getFaces() {	
		return faces;
	}

	public void uploadImage(String imagename){
		String url = "http://www.groepfotoboek.nl/robot/upload.php";
		//ImageToWeb img_to_web = new ImageToWeb(p);
		//img_to_web.post("uploadedfile",url,imagename,true);
	}

	//Delay in milliseconds
	//saved nu naar bin/filename.gif
	public void maakGif(String filename, int frames, int delay) {
		/*		gif = new GifMaker(p,filename);
		gif.setRepeat(0);
		for(int i = 0; i < frames; i++){
			gif.setDelay(delay);
			gif.addFrame();
		}
		gif.finish();*/
	}

	//Returns average RGB of the current screen
	public int[] pixels(){
		/*		float red = 0;
		float green = 0;
		float blue = 0;
		p.loadPixels();
		for(int i = 0; i < p.pixels.length; i++){
			red += (p.pixels[i] >> 16) & 0xFF;
			green += (p.pixels[i] >> 8) & 0xFF;
			blue += p.pixels[i] & 0xFF;
		}
		red /= p.pixels.length;
		green /= p.pixels.length;
		blue /= p.pixels.length;
		int[] colors = {(int)red,(int)green,(int)blue};
		return colors;*/return null;
	}

	//or in a certain area
	public int[] pixels(int x,int y,int w,int h){
		/*		float red = 0;
		float green = 0;
		float blue = 0;
		if(w > p.width){
			w = p.width;
		}
		if(h > p.height){
			h = p.height;
		}
		int temp = p.width + x + 1;
		p.loadPixels();
		for (int i = y; i < y+h ; i++){
			for(int j = i*p.width + x + 1 ; j < i*p.width + x + 1 + w ; j++){
				red += (p.pixels[j] >> 16) & 0xFF;
				green += (p.pixels[j] >> 8) & 0xFF;
				blue += p.pixels[j] & 0xFF;
			}
		}
		red /= w*h;
		green /= w*h;
		blue /= w*h;
		int[] colors = {(int)red,(int)green,(int)blue};
		return colors;*/ return null;
	}

	public class NoInputAvailableException extends Exception{
		private static final long serialVersionUID = 1L;
		public NoInputAvailableException(String msg){
			super(msg);
		}
	}
}
