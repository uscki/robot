import java.awt.Color;
import java.awt.image.BufferedImage;
import lib.Image;

public class BufferedImageTest {
	public static void main(String[] args){
		
		BufferedImage img = Image.loadImageFromWeb("http://www.messagefrommasters.com/Osho/images/Osho-on-adolf-hitler(1).jpg");

		BufferedImage snor = Image.loadImage("snor.png");
		
		snor = Image.scale(snor, 0.7, 0.7);
		
		Image.drawImage(img,snor,80,160);
		
		if(img == null){
			System.out.println("oops");
		} else {
			Image.saveImage(img,"webcamtest.jpg");
		}
	}
}
