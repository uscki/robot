package lib;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.nio.IntBuffer;
import java.util.Iterator;
import java.util.List;

import mennov1.MennoV1.OnResizeListener;

import org.gstreamer.Bus;
import org.gstreamer.Element;
import org.gstreamer.ElementFactory;
import org.gstreamer.Gst;
import org.gstreamer.GstObject;
import org.gstreamer.Pad;
import org.gstreamer.Pipeline;
import org.gstreamer.State;
import org.gstreamer.elements.RGBDataSink;
import org.gstreamer.swing.VideoComponent;

public class Webcam implements RGBDataSink.Listener{

	private static Pipeline pipe;
	private Element webcamElement, videoCompElement, imageCaptureElement, tee;
	private VideoComponent videoComponent;
	private String capturePlugin, devicePropertyValue;
	private int width, height;
	private BufferedImage screenshot = null;
	private OnResizeListener resizeListener = null;

	public Webcam() throws OSNotDetectedException{
		
		//detect os
		switch(getOS()){
		case WIN:
			capturePlugin = "ksvideosrc";
			devicePropertyValue = "device-name";
			break;
		case NIX:
			capturePlugin = "v4l2src";
			devicePropertyValue = "/dev/video0";
			break;
		case OSX_32:
			capturePlugin = "osxvideosrc";
			devicePropertyValue = "device-name";
			break;
		case OSX_64:
			capturePlugin = "qtkitvideosrc";
			devicePropertyValue = "device-name";
			break;
		default:
		case UNKNOWN:
			throw new OSNotDetectedException();
		}

		Gst.init();
	}

	public VideoComponent getVideoComponent(){
		if(videoComponent == null) setupPipe();
		return videoComponent;
	}
	
	public BufferedImage getScreenshot(){
		return screenshot;
	}
	
	public void setOnResizeListener(OnResizeListener _listener){
		resizeListener = _listener;
	}

	public void setupPipe(){
		webcamElement = ElementFactory.make(capturePlugin, "source");
		//webcamElement.set("device", devicePropertyValue);//TODO
		webcamElement.set("always-copy", false);

		videoComponent = new VideoComponent();
		videoComponent.setPreferredSize(new Dimension(width, height));
		videoCompElement = videoComponent.getElement();

		imageCaptureElement = new RGBDataSink("VideoCapture", this);

		tee = ElementFactory.make("tee", "tee");		
		
		
		pipe = new Pipeline("Webcam to videoComponent");
		pipe.addMany(webcamElement, tee, videoCompElement, imageCaptureElement);
		
		Element.linkMany(webcamElement, tee);
		
		tee.link(videoCompElement);
		tee.link(imageCaptureElement);
		
		Bus bus = pipe.getBus();
		bus.connect(new Bus.EOS() {
			public void endOfStream(GstObject source) {
				System.out.println("EOS");
				pipe.setState(State.NULL);
				Gst.quit();
			}
		});
		bus.connect(new Bus.ERROR() {
			public void errorMessage(GstObject arg0, int arg1, String arg2) {
				System.out.println("Error: " + arg2 + " " + arg0);
				Gst.quit();
			}
		});
		printPipeline(pipe);
		pipe.setState(State.PLAYING);
	}

	private OS getOS(){
		String os = System.getProperty("os.name");
		if(os.contains("win")){
			return OS.WIN;
		}else if (os.contains("mac")){
			if(Integer.parseInt(System.getProperty("sun.arch.data.model")) == 32){
				return OS.OSX_32;
			}else {//lets assume 64 :)
				return OS.OSX_64;
			}
		}else if (os.contains("nix") || os.contains("nux")){
			return OS.NIX;
		}else{
			return OS.UNKNOWN;
		}
	}
	/* Iterate through all the elements in the pipeline and display their
	 * caps. Useful for debugging and general information about the pipeline */
	public static void printPipeline(Pipeline p) {
		
		List<Element> elements = p.getElements();
		
		if (elements.size() > 0) {
			Iterator<Element> elemiter = elements.iterator();
			Element e = null;
			while (elemiter.hasNext()) {
				e = (Element) elemiter.next();

				List<Pad> pads = e.getPads();
				
				if (pads.size() > 0) {
					Iterator<Pad> paditer = pads.iterator();
					Pad pad = null;
					while (paditer.hasNext()) {
						pad = (Pad) paditer.next();
						System.out.print(e + " " + pad.getDirection()); 
						System.out.println("\t" + pad.getCaps());
					}
				}
			}
		}
	}
	public synchronized void rgbFrame(boolean isPrerollFrame, int _width, int _height, IntBuffer rgb) {
		if(resizeListener != null && (width != _width || height != _height)){
			width = _width;
			height = _height;
			resizeListener.onResize(width, height);
		}
		
		screenshot = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] pixels = ((DataBufferInt)screenshot.getRaster().getDataBuffer()).getData();
		rgb.get(pixels, 0, width*height);
	}
}

class OSNotDetectedException extends Exception{
	private static final long serialVersionUID = 1L;

}

enum OS{
	WIN, NIX, OSX_32, OSX_64, UNKNOWN;
}