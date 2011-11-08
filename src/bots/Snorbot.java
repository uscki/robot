package bots;

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
				
				int w = l.getFaces()[0].width;
		    	int h = l.getFaces()[0].height;
		    	int x = l.getFaces()[0].x + (w/4);
		    	int y = l.getFaces()[0].y + (h/2) + (h/10);
		    	w = w/2;
		    	h = h/4;
		    	l.tekenPlaatje(l.getImages(0),x, y, w, h );
		    	l.uploadImage("snorbot");			
				return "http://www.groepfotoboek.nl/robot/saved/uploadedfile/snorbot.jpg";
			} else {
				return "Ik zie niemand...";
			}		
		}
		return null;
	}
}
