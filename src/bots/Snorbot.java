package bots;

import java.awt.Rectangle;
import lib.Looker;

public class Snorbot implements Bot {
	
	public String ask(String input, String user) {
		
		if(input.contains("snor")){
			Looker l = Looker.getInstance();
			if (l.seesFace()) {
				System.out.println("Looker ziet wat!");
				
				String[] s = {"snor.png"};
				l.plaatjes(s);
				//l.images[0] is nu snor.png
				for(Rectangle r : l.getFaces()){
					int w = r.width;
			    	int h = r.height;
			    	int x = r.x + (w/4);
			    	int y = r.y + (h/2) + (h/10);
			    	w = w/2;
			    	h = h/4;
			    	int[] colors = l.pixels(r.x,r.y,r.width,h/2);
			    	l.tint(colors[0],colors[1],colors[2],200);
			    	l.tekenPlaatje(l.getImages(0),x, y, w, h );
			    	l.noTint();
				}
		    	l.uploadImage("snorbot");	
				return "http://www.groepfotoboek.nl/robot/saved/uploadedfile/snorbot.jpg";
			} else {
				return "Ik zie niemand...";
			}		
		}
		return null;
	}
}
