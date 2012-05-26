import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.seltar.Bytes2Web.ImageToWeb;

import jjil.core.Rect;

import lib.FaceDetection;

import lib.Image;

public class BufferedImageTest {
	public static void main(String[] args){
		
		BufferedImage img = Image.loadImageFromWeb("http://4.bp.blogspot.com/-pNqA0Cx6g-8/TWHANiJEiXI/AAAAAAAAAFc/M8C0nzw6xzM/s1600/mona-lisa.jpg");
		//BufferedImage img = Image.loadImage("test.jpg");
		
		List<Rectangle> faceList = FaceDetection.findFaces(img, 1, 40);
		
		BufferedImage snor = Image.loadImage("snor.png");
		
		//snor = Image.scale(snor, 0.65, 0.65);
		//System.out.println(faceList.toString());
		
		for(Rectangle face : faceList){
						
			int x = face.x + (int)(0.25 * face.width);
			int y = face.y + (int)(0.65 * face.height);
			
			double sx = (face.width * 0.5) / snor.getWidth();
			double sy = (face.height * 0.25) / snor.getHeight();
					
			snor = Image.scale(snor, sx, sy);
			
			Image.drawImage(img,snor,x,y);
		}
		
		if(img == null){
			System.out.println("oops");
		} else {
			Image.saveImage(img,"webcamtest.jpg");
		}
	}
}
