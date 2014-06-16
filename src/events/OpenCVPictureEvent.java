package events;

import java.awt.image.BufferedImage;
import org.bytedeco.javacv.cpp.opencv_core.IplImage;

/**
 * Plaatje zien met OpenCV, ander beeldformaat (snelheid)
 * 
 * @author Benno Kruit
 *
 */

public class OpenCVPictureEvent extends PictureEvent {
  
  private BufferedImage image;
  private IplImage ipl_image;
  
  public OpenCVPictureEvent(Object source, IplImage ipl_img) {
    super(source, ipl_img.getBufferedImage());
    this.ipl_image = ipl_img;
  } 
  
  public IplImage getIplImage()
  {
    return ipl_image;
  }
}
