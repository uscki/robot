package lib;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import jjil.algorithm.Gray8Rgb;
import jjil.algorithm.RgbAvgGray;
import jjil.core.Image;
import jjil.core.Rect;
import jjil.core.RgbImage;
import jjil.j2se.RgbImageJ2se;

public class FaceDetection {
	
	/**
	 * 
	 * Naar http://www.richardnichols.net/2011/01/java-facial-recognition-haar-cascade-with-jjil-guide/
	 * Belangrijk: zet JJILCore.jar en JJIL-J2SE.jar in Build Path!
	 * Voorbeeldaanroep:
	 * BufferedImage bi = ImageIO.read(Main.class.getResourceAsStream("test.jpg"));
     * findFaces(bi, 1, 40, new File("c:/users/sjoerd/desktop/result.jpg")); // change as needed
	 */

    public static List<Rectangle> findFaces(BufferedImage bi, int minScale, int maxScale){//, File output) {
        try {
            InputStream is  = FaceDetection.class.getResourceAsStream("/lib/haar/HCSB.txt");
            Gray8DetectHaarMultiScale detectHaar = new Gray8DetectHaarMultiScale(is, minScale, maxScale);
            RgbImage im = RgbImageJ2se.toRgbImage(bi);
            RgbAvgGray toGray = new RgbAvgGray();
            toGray.push(im);
            List<Rect> results = detectHaar.pushAndReturn(toGray.getFront());
            List<Rectangle> faces = rectToRectangle(results); 
            faces = filterFaces(faces);
            System.out.println("Found "+faces.size()+" faces");
            Image i = detectHaar.getFront();
            Gray8Rgb g2rgb = new Gray8Rgb();
            g2rgb.push(i);
            //RgbImageJ2se conv = new RgbImageJ2se();
            //conv.toFile((RgbImage)g2rgb.getFront(), output.getCanonicalPath());
            return faces;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
        
    }
    
    public static List<Rectangle> filterFaces(List<Rectangle> list){
    	
    	List<Rectangle> target = new ArrayList<Rectangle>();
    	
    check:	
    	for (Rectangle listR : list){
	    	for (Rectangle targetR : target){
				if (targetR.contains(listR)){
					break check;
				}
			}
	    	target.add(listR);
    	}
    	
    	return target;
    }
    
    public static List<Rectangle> rectToRectangle(List<Rect> results) {
    	
    	List<Rectangle> faces = new ArrayList<Rectangle>();
    	for(Rect r : results){
    		faces.add(new Rectangle(r.getLeft(),r.getTop(),r.getWidth(),r.getHeight()));
    	}
    	
    	return faces;
    }

}