package lib;

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
	 * Voorbeeldaanroep:
	 * BufferedImage bi = ImageIO.read(Main.class.getResourceAsStream("test.jpg"));
     * findFaces(bi, 1, 40, new File("c:/users/sjoerd/desktop/result.jpg")); // change as needed
	 */

    public static List<Rect> findFaces(BufferedImage bi, int minScale, int maxScale){//, File output) {
    	//TODO gezichten filteren, er zitten nog dubbele tussen
        try {
            InputStream is  = FaceDetection.class.getResourceAsStream("/lib/haar/HCSB.txt");
            if (is==null){System.out.println("Shit has hit the fan");}
            Gray8DetectHaarMultiScale detectHaar = new Gray8DetectHaarMultiScale(is, minScale, maxScale);
            RgbImage im = RgbImageJ2se.toRgbImage(bi);
            RgbAvgGray toGray = new RgbAvgGray();
            toGray.push(im);
            List<Rect> results = detectHaar.pushAndReturn(toGray.getFront());
            //System.out.println("Found "+results.size()+" faces");
            Image i = detectHaar.getFront();
            Gray8Rgb g2rgb = new Gray8Rgb();
            g2rgb.push(i);
            //RgbImageJ2se conv = new RgbImageJ2se();
            //conv.toFile((RgbImage)g2rgb.getFront(), output.getCanonicalPath());
            return results;
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

}