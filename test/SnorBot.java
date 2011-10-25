import processing.core.*;

public class SnorBot extends PApplet {
	
	PImage i, snor;
	
	public void setup(){
		size(640,400); //venster eigenlijk niet nodig?
		noLoop();
		//load plaatjes in
		i = loadImage("webcamOutput.png");
		snor = loadImage("snor.png");
		println("Brushing 'stache... please wait...");
	}
	
	public void draw() {
		//laat plaatje zien
		image(i,0,0);
		//plak snor
		//argumenten: x, y, width, height
		//plaatsing/scaling met herkenning? -> openCV nodig
		image(snor,260,265,170,60);
		PImage upload = get();
		image(upload,0,0);
		//gebruik postToWeb om plaatje op de server te zetten

		//en geef een link terug
	}

}
